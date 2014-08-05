package br.unicamp.iel.tool.viewparameters;

import lombok.AllArgsConstructor;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;

@AllArgsConstructor
public class JustificationViewParameters extends SimpleViewParameters {
    public Long course;
    public Long module;
    public Long activity;
    public Long question;
    public Long exercise;

    public JustificationViewParameters(){}

    public JustificationViewParameters(String viewID){
        super();
        this.viewID = viewID;
    }

    public JustificationViewParameters(Long course, Long module, Long activity) {
        super();
        this.course = course;
        this.module = module;
        this.activity = activity;
    }

}
