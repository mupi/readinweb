package br.unicamp.iel.tool.viewparameters;

import lombok.AllArgsConstructor;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;

@AllArgsConstructor
public class CourseViewParameters extends SimpleViewParameters {
    public Long course;
    public Long module;
    public Long activity;
    public Long question;
    public Long exercise;

    public CourseViewParameters(){}

    public CourseViewParameters(String viewID){
        super();
        this.viewID = viewID;
    }

    public CourseViewParameters(Long course, Long module, Long activity) {
        super();
        this.course = course;
        this.module = module;
        this.activity = activity;
    }

}
