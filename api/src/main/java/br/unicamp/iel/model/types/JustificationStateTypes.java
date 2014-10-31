package br.unicamp.iel.model.types;

import lombok.Getter;

public enum JustificationStateTypes {
    UNREAD_MESSAGES(new Byte((byte)1)),
    EVALUATED(new Byte((byte)2));

    @Getter
    private Byte value;
    private JustificationStateTypes(Byte value){
        this.value = value;
    }

    public static boolean toShow(Byte sum){
        return sum != 2;
    }

    public static Byte markEvaluated(Byte sum){
        if(sum.byteValue() < 2){
            return new Byte((byte)(sum + EVALUATED.getValue()));
        }
        return sum;
    }

    public static Byte flagUnread(Byte sum){
        if(sum.byteValue() % 2 == 0){
            return new Byte((byte)(sum + UNREAD_MESSAGES.getValue()));
        }
        return sum;
    }

    public static Byte markAsRead(Byte sum){
        if(sum.byteValue() % 2 == 1){
            return new Byte((byte)(sum - UNREAD_MESSAGES.getValue()));
        }
        return sum;
    }

}
