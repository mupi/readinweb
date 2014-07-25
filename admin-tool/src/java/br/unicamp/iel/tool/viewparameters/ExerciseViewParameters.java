package br.unicamp.iel.tool.viewparameters;

import lombok.AllArgsConstructor;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;

@AllArgsConstructor
public class ExerciseViewParameters extends SimpleViewParameters {
    public Long course;
    public Long module;
    public Long activity;
    public Long exercise;
    
    public ExerciseViewParameters(String viewID){
        this.viewID = viewID;
    }
    
    public ExerciseViewParameters(){};
    
}
