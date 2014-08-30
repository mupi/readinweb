package br.unicamp.iel.tool.viewparameters;

import lombok.AllArgsConstructor;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;

@AllArgsConstructor
public class CourseViewParameters extends SimpleViewParameters {
    public boolean newdata;
    public boolean dataupdated;
    public boolean datadeleted;
    public Long course;
    public Long activity;
    public Long exercise;


    public Long question;
    public Long module;
    public String message;

    public CourseViewParameters(){}

    public CourseViewParameters(String viewID){
        super();
        this.viewID = viewID;
    }

    public CourseViewParameters(Long course, Long activity) {
        super();
        this.course = course;
        this.activity = activity;
    }

    public CourseViewParameters(Long course, Long exercise, Long activity) {
        super();
        this.course = course;
        this.exercise = exercise;
        this.activity = activity;
    }

    // FIXME create a better way to handle submit flow

    public CourseViewParameters(String viewId, boolean newActivity,
            boolean updatedActivity, boolean deletedActivity){
        super(viewId);
        this.newdata = newActivity;
        this.dataupdated = updatedActivity;
        this.datadeleted = deletedActivity;
    }
}
