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
            return sum == 1 || sum == 3 || sum == 5;
        case EXERCISE:
            return sum == 2 || sum == 3 || sum == 6;
        case QUESTIONS:
            return sum == 4 || sum == 5 || sum == 6;
        default:
            return false;
        }
    }

    public static Integer getSum() {
        Integer sum = 0;
        for(ControlTypes ct : ControlTypes.values()){
            sum += ct.getValue();
        }
        return sum;
    }
}
