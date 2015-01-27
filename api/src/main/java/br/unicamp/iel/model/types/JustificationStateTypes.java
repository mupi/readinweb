package br.unicamp.iel.model.types;

import lombok.Getter;

public enum JustificationStateTypes {
    UNREAD_MESSAGES(new Byte((byte)1)),
    EVALUATED(new Byte((byte)2)),
    NOTACCEPTED(new Byte((byte)(4)));

    @Getter
    private Byte value;
    private JustificationStateTypes(Byte value){
        this.value = value;
    }

    public static boolean toShow(Byte sum){
        return sum != 2 && sum != 4;
    }
    
    public static boolean isOld(Byte sum) {
    	return sum >= 2;
    }

    public static Byte markEvaluated(Byte sum, boolean accepted){
    	Byte state = new Byte((byte)0);
        if(sum.byteValue() < 2){
            sum = new Byte((byte)(sum + EVALUATED.getValue()));
        }
        if(!accepted && sum.byteValue() < 4){
        	sum = new Byte((byte)(state + NOTACCEPTED.getValue()));
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
