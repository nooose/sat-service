package com.sat.study.application.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StudyGroupTitleValidator implements ConstraintValidator<StudyGroupTitle, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return com.sat.study.domain.StudyGroupTitle.isValid(value);
    }
}
