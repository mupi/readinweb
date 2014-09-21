package br.unicamp.iel.model.types;

import lombok.Getter;

public enum BlockStateTypes {
    UNBLOCKED(new Byte((byte)0)),
    BLOCKED(new Byte((byte)1)),
    REMISSION(new Byte((byte)2));

    @Getter
    private Byte value;
    private BlockStateTypes(Byte value){
        this.value = value;
    }
    public static boolean isUserBlocked(Byte state) {
        return BLOCKED.getValue().equals(state);
    }

    public static boolean isUserRemission(Byte state) {
        return REMISSION.getValue().equals(state);
    }
}
