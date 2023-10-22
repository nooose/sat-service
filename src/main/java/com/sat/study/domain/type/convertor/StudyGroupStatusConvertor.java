package com.sat.study.domain.type.convertor;

import com.sat.study.domain.type.StudyGroupStatus;
import jakarta.persistence.Converter;

@Converter
public class StudyGroupStatusConvertor extends CodeEnumConvertor<StudyGroupStatus> {
    public StudyGroupStatusConvertor() {
        super(StudyGroupStatus.class);
    }
}
