package br.unicamp.iel.tool;

import lombok.Data;
import br.unicamp.iel.logic.ReadInWebCourseLogic;

@Data
public class SessionBean {
  
    public Long course;
    public Long module;
    private Long[] modules;
    public Long activity;
    private Long exercise;
    private boolean blocked = false;
    private String printTurmaId;
    
    //private ReadInWebCourseLogic logic;
    
    public void setPrintTurmaId(String printTurmaId) {
//        if (this.logic.isUserAdmin() || this.logic.isUserTeacher(this.logic.getUserId())) {
//            this.printTurmaId = printTurmaId;
//        }
    }

    public String getPrintTurmaId() {
//        if (this.logic.isUserAdmin() || this.logic.isUserTeacher(this.logic.getUserId())) {
//            return this.printTurmaId;
//        }
        return null;
    }
    
    @Override
    public String toString(){
        String data = "Session Bean:\n"
                + "course: " + course + "\n"
                + "module: " + module + "\n"
                + "activity: " + activity + "\n";
        return data;  
    }
    
    public void init(){
        return;
    }
}
