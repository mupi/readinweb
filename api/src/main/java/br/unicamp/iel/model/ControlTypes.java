package br.unicamp.iel.model;

import lombok.Getter;

public enum ControlTypes {
    TEXT(1),
    EXERCISE(2),
    QUESTIONS(4);

    @Getter
    private Integer value;
    private ControlTypes(Integer value){
        this.value = value;
    }

    public static boolean hasDone(ControlTypes type, Integer sum){
        if(sum == 7) return true;

        switch (type) {
        case TEXT:
            if(sum == 1 || sum == 3 || sum == 5)
                return true;
        case EXERCISE:
            if(sum == 2 || sum == 3 || sum == 6)
                return true;
        case QUESTIONS:
            if(sum == 4 || sum == 5 || sum == 6)
                return true;
        default:
            return false;
        }
    }
}
