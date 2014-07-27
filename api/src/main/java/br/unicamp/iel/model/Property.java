package br.unicamp.iel.model;

import lombok.Getter;
import lombok.Setter;

public enum Property {
    COURSE("readinwebcourse"),
    COURSEDATA("readinwebcourse.data");

    @Setter
    @Getter
    String name;

    private Property(String name){
        this.name = name;
    }
}
