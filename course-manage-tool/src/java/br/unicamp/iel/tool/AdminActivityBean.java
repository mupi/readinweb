package br.unicamp.iel.tool;

import lombok.Data;
import lombok.Setter;
import br.unicamp.iel.logic.ReadInWebClassManagementLogic;

@Data
public class AdminActivityBean {
    private Long module;
    private Long activity;

    @Setter
    private ReadInWebClassManagementLogic logic;

    public boolean updateActivity(Long id){
        return false;
    }

    public boolean dataSent() {
        return module != null || activity != null;
    }
}
