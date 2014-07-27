package br.unicamp.iel.tool;

import lombok.Data;
import lombok.Setter;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Activity;

@Data
public class AdminActivityBean {
    private Integer position;
    private String image;
    private String title;
    private String text;
    private String preReading;
    private Integer etaRead;

    @Setter
    private ReadInWebAdminLogic logic;

    public boolean updateActivity(Long id){
        Activity activity = logic.getActivity(id);

        if(position != null){
            activity.setPosition(position);
        }
        if(title != null){
            activity.setTitle(title);
        }
        if(text != null){
            activity.setText(text);
        }
        if(preReading != null){
            activity.setPrereading(preReading);
        }
        if(etaRead != null){
            activity.setEtaRead(etaRead);
        }

        return logic.updateActivity(activity);
    }

    public boolean dataSent() {
        return position != null || image != null || title != null
                || text != null || preReading != null || etaRead != null;
    }
}
