package com.sh.lulu.common.modeConvertor;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface EnumValue <E> {
    E getCode();
}
