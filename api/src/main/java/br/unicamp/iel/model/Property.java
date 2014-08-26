package br.unicamp.iel.model;

import lombok.Getter;
import lombok.Setter;

public enum Property {
    COURSEMANAGED("readinwebmanager"),
    USERDATA("readinwebuser.data"),
    COURSE("readinwebcourse"),
    COURSEDATA("readinwebcourse.data"),
    COURSESTARTDATE("readinwebcourse.startdate"),
    COURSEREMISSIONTIME("readinwebcourse.remissiontime"),
    COURSEFINISHED("readinwebcourse.finished");

    @Setter
    @Getter
    String name;

    private Property(String name){
        this.name = name;
    }
}
