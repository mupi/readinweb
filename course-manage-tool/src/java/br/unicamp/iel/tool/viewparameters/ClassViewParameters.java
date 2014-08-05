package br.unicamp.iel.tool.viewparameters;

import uk.org.ponder.rsf.viewstate.SimpleViewParameters;

public class ClassViewParameters extends SimpleViewParameters {
    public String siteId;

    public ClassViewParameters(){}

    public ClassViewParameters(String viewID){
        super();
        this.viewID = viewID;
    }
}
