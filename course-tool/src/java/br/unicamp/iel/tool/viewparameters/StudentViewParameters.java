package br.unicamp.iel.tool.viewparameters;

import br.unicamp.iel.tool.producers.StudentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;

public class StudentViewParameters extends SimpleViewParameters {
    public String userId;

    public StudentViewParameters(String userId){
    	this.viewID = StudentProducer.VIEW_ID;
        this.userId = userId;
    }
    
    public StudentViewParameters(){};
    
}
