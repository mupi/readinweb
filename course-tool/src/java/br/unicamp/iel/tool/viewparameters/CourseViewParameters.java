package br.unicamp.iel.tool.viewparameters;

import lombok.AllArgsConstructor;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;

@AllArgsConstructor
public class CourseViewParameters extends SimpleViewParameters {
    public Long course;
    public Long module;
    public Long activity;

    public CourseViewParameters(String viewID){
        this.viewID = viewID;
    }
    
    public CourseViewParameters(){};
    
}
