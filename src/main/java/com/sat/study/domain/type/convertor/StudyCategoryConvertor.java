package com.sat.study.domain.type.convertor;

import com.sat.study.domain.type.StudyCategory;
import jakarta.persistence.Converter;

@Converter
public class StudyCategoryConvertor extends CodeEnumConvertor<StudyCategory> {
    public StudyCategoryConvertor() {
        super(StudyCategory.class);
    }
}
