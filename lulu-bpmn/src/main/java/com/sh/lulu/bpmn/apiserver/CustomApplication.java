package com.sh.lulu.bpmn.apiserver;

import com.sh.lulu.common.response.CommonResult;
import org.camunda.bpm.cockpit.impl.web.CockpitApplication;
import org.camunda.bpm.engine.rest.dto.ExceptionDto;
import org.camunda.bpm.engine.rest.exception.ExceptionHandlerHelper;
import org.camunda.bpm.tasklist.impl.web.TasklistApplication;
import org.camunda.bpm.webapp.impl.engine.EngineRestApplication;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Iterator;
import java.util.Set;

public class CustomApplication {

    public static class CustomCockpitApplication extends CockpitApplication {
        @Override
        public Set<Class<?>> getClasses() {
            return ModifyClass(super.getClasses());
        }
    }

    public static class CustomTaskListApplication extends TasklistApplication {
        @Override
        public Set<Class<?>> getClasses() {
            return ModifyClass(super.getClasses());
        }
    }

    public static class CustomEngineRestApplication extends EngineRestApplication {
        @Override
        public Set<Class<?>> getClasses() {
            return ModifyClass(super.getClasses());
        }
    }

    public static Set<Class<?>> ModifyClass(Set<Class<?>> origin) {
        origin.add(CustomMessageWriter.class);
        // remove default exceptionMapper
        Iterator<Class<?>> iterator = origin.iterator();
        while (iterator.hasNext()) {
            Class<?> aClass = iterator.next();
            if (ExceptionMapper.class.isAssignableFrom(aClass)) {
                iterator.remove();
            }
        }
        origin.add(CustomExceptionMapper.class);

        return origin;
    }

    @Provider
    public static class CustomExceptionMapper implements ExceptionMapper<Throwable> {

        @Override
        public Response toResponse(Throwable throwable) {
            Response response = ExceptionHandlerHelper.getInstance().getResponse(throwable);
            ExceptionDto dto = (ExceptionDto) response.getEntity();
            CommonResult failed = CommonResult.failed(dto.getMessage());
            failed.setCode(dto.getCode());
        return Response.status(response.getStatus()).entity(failed).build();
        }
    }
}
