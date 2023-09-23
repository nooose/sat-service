package com.sat.study.application.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.sat.study.domain.StudyGroupTitle.MAX_LENGTH;
import static com.sat.study.domain.StudyGroupTitle.MIN_LENGTH;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StudyGroupTitleValidator.class})
public @interface StudyGroupTitle {

    String message() default "스터디그룹 제목의 길이는 " + MIN_LENGTH + "이상 " + MAX_LENGTH + "이하이어야 합니다.";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
