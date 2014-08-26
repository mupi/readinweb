package br.unicamp.iel.model.types;

import lombok.Getter;

public enum ContentTypes {
    MODULE("Modulos (gramáticas)"),
    FUNCTIONALWORD("Palavras Função"),
    ACTIVITY("Atividade"),
    QUESTIONS("Questões"),
    EXERCISE("Exercicios"),
    DICTIONARY("Dicionário"),
    STRATEGY("Estrategias");

    @Getter
    private String title;
    private ContentTypes(String title){
        this.title = title;
    }
}
