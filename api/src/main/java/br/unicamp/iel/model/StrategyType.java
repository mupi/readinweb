package br.unicamp.iel.model;

import lombok.Getter;

public enum StrategyType {
    READING(0) { // le, 0
        @Override
        public String title() {
            return "Estratégia de leitura";
        }
    }, LANGUAGE(1) { // li, 1
        @Override
        public String title() {
            return "Estratégia de aquisição de língua";
        }
    };
    
    @Getter
    private java.lang.Integer value;
    
    public abstract String title();  
    
    private StrategyType(Integer value){
        this.value = value;
    }

}
