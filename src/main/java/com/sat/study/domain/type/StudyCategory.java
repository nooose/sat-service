package com.sat.study.domain.type;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum StudyCategory implements CodeEnum {
    IT("IT", "IT", null),
    PROGRAMMING_LANGUAGE("LANG", "프로그래밍 언어", IT),
    C("C", "C", PROGRAMMING_LANGUAGE),
    C_PLUS_PLUS("C++", "C++", PROGRAMMING_LANGUAGE),
    JAVA("JAVA", "Java", PROGRAMMING_LANGUAGE),
    PYTHON("PYTHON", "Python", PROGRAMMING_LANGUAGE),
    KOTLIN("KOTLIN", "Kotlin", PROGRAMMING_LANGUAGE),
    GO("GO", "Go", PROGRAMMING_LANGUAGE),

    FOREIGN_LANGUAGE("FOREIGN_LANGUAGE", "외국어", null),
    ENGLISH("ENG", "영어", FOREIGN_LANGUAGE),
    JAPANESE("JPN", "일본어", FOREIGN_LANGUAGE),
    SPANISH("SPANISH", "스페인어", FOREIGN_LANGUAGE);

    private final String code;
    private final String title;
    private final StudyCategory parent;
    private final List<StudyCategory> childCategories;

    StudyCategory(String code, String title, StudyCategory parent) {
        this.code = code;
        this.title = title;
        this.parent = parent;
        this.childCategories = new ArrayList<>();
        if (parent != null) {
            parent.addChild(this);
        }
    }

    private void addChild(StudyCategory studyCategory) {
        childCategories.add(studyCategory);
    }

    public boolean isLeaf() {
        return childCategories.isEmpty();
    }
}
