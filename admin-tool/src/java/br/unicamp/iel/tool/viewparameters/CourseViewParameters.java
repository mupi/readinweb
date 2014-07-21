package br.unicamp.iel.tool.viewparameters;

import uk.org.ponder.rsf.viewstate.SimpleViewParameters;

public class CourseViewParameters extends SimpleViewParameters {
    public Long id;
    public String title;
    public String idiom;
    public String description;
    
    public CourseViewParameters(String viewID) {
        super(viewID);
    }
    
}
