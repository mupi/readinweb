package br.unicamp.iel.tool.viewparameters;

import lombok.Data;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;

public class JustificationViewParameters extends SimpleViewParameters {
    public Long justification;

    public JustificationViewParameters(){}

    public JustificationViewParameters(String viewId, Long justification){
        super(viewId);
        this.justification = justification;
    }
}
