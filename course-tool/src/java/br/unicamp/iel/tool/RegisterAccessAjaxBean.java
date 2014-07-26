package br.unicamp.iel.tool;

import uk.org.ponder.rsf.state.scope.BeanDestroyer;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import lombok.Data;

@Data
public class RegisterAccessAjaxBean {
    private Long activity;

    private ReadInWebCourseLogic logic;
    private BeanDestroyer destroyer;

    private String[] results;
    public String[] getResults(){
        registerTextRead();
        destroyer.destroy();
        return results;
    }

    public void registerTextRead(){
        try{
            logic.registerTextRead(activity);
            results = new String[] {"success"};
        } catch (Exception e){
            e.printStackTrace();
            results = new String[] {"error"};
        }
    }
}
