package com.sat.study.domain.type.convertor;

import com.sat.study.domain.type.ParticipantStatus;
import jakarta.persistence.Converter;

@Converter
public class ParticipantStatusConvertor extends CodeEnumConvertor<ParticipantStatus> {
    public ParticipantStatusConvertor() {
        super(ParticipantStatus.class);
    }
}
