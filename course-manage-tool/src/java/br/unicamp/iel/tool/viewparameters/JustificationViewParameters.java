package br.unicamp.iel.tool.viewparameters;

import uk.org.ponder.rsf.viewstate.SimpleViewParameters;


public class JustificationViewParameters extends SimpleViewParameters {
    public String siteId;

    public JustificationViewParameters(){}

    public JustificationViewParameters(String viewID){
        super();
        this.viewID = viewID;
    }

}
