package com.sat.study.domain.type.convertor;

import com.sat.study.domain.type.CodeEnum;
import jakarta.persistence.AttributeConverter;

abstract class CodeEnumConvertor<T extends Enum<T> & CodeEnum> implements AttributeConverter<T, String> {

    private final Class<T> supprotClass;

    public CodeEnumConvertor(Class<T> supprotClass) {
        this.supprotClass = supprotClass;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return attribute.getCode();
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        return CodeEnum.of(supprotClass, dbData);
    }
}
