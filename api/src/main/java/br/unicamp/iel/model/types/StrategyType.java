package br.unicamp.iel.model.types;

import lombok.Getter;

public enum StrategyType {
    READING(0, "Estratégia de leitura"),
    LANGUAGE(1, "Estratégia de aquisição de língua");

    @Getter
    private Integer value;
    @Getter
    private String title;

    private StrategyType(Integer value, String title){
        this.title = title;
        this.value = value;
    }

}
