package br.unicamp.iel.model.types;

import lombok.Getter;

public enum AccessTypes {
    TEXT("Accesso ao texto"),
    EXERCISE("Acesso a exercicio"),
    STRATEGY("Acesso a estrategia"),
    GRAMMAR("Acesso a gramatica");

    @Getter
    private String title;
    private AccessTypes(String title){
        this.title = title;
    }
}
