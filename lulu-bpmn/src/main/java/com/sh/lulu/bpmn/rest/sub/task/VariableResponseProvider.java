/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sh.lulu.bpmn.rest.sub.task;

import com.sh.lulu.bpmn.rest.util.URLEncodingUtil;
import org.camunda.bpm.engine.variable.type.ValueType;
import org.camunda.bpm.engine.variable.value.BytesValue;
import org.camunda.bpm.engine.variable.value.FileValue;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.Charset;

public class VariableResponseProvider {

    public ResponseEntity<?> getResponseForTypedVariable(TypedValue typedVariableValue, String id) {
        if (typedVariableValue instanceof BytesValue || ValueType.BYTES.equals(typedVariableValue.getType())) {
            return responseForByteVariable(typedVariableValue);
        } else if (ValueType.FILE.equals(typedVariableValue.getType())) {
            return responseForFileVariable((FileValue) typedVariableValue);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Value of variable with id %s is not a binary value.", id));
        }
    }


    protected ResponseEntity<?> responseForFileVariable(FileValue fileValue) {
        MediaType type = fileValue.getMimeType() != null ? MediaType.parseMediaType(fileValue.getMimeType()) : MediaType.APPLICATION_OCTET_STREAM;
        if (fileValue.getEncoding() != null) {
            type = new MediaType(type, Charset.forName(fileValue.getEncoding()));
        }
        Object value = fileValue.getValue() == null ? "" : fileValue.getValue();
        return ResponseEntity.ok().header("Content-Disposition", URLEncodingUtil.buildAttachmentValue(fileValue.getFilename()))
                .contentType(type)
                .body(value);
    }

    protected ResponseEntity<byte[]> responseForByteVariable(TypedValue variableInstance) {
        byte[] valueBytes = (byte[]) variableInstance.getValue();
        if (valueBytes == null) {
            valueBytes = new byte[0];
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(valueBytes);
    }
}
