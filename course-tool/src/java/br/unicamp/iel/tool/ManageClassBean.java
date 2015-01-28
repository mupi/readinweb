package br.unicamp.iel.tool;

import lombok.Data;
import lombok.Setter;
import br.unicamp.iel.logic.ReadInWebClassManagementLogic;
import br.unicamp.iel.logic.ReadInWebNavigationCases;

@Data
public class ManageClassBean {
    public String riwClass;
    public Boolean classState;

    @Setter
    private ReadInWebClassManagementLogic logic;

    public String changeClassState(){
        if(changeClassStatedataSent()){
            logic.setClassState(logic.getReadInWebClass(riwClass), classState);
            return ReadInWebNavigationCases.UPDATED;
        } else {
            return ReadInWebNavigationCases.DATA_EMPTY;
        }
    }

    public boolean changeClassStatedataSent() {
        return classState != null || riwClass != null;
    }
}
