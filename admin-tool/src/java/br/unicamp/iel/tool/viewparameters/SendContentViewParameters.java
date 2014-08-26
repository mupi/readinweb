package br.unicamp.iel.tool.viewparameters;

import lombok.AllArgsConstructor;
import lombok.Setter;
import br.unicamp.iel.tool.producers.AdminSendContentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;

@AllArgsConstructor
public class SendContentViewParameters extends SimpleViewParameters{
    public boolean dataPosted;
    public String errorMessage;

    public SendContentViewParameters(){
        this.viewID = AdminSendContentProducer.VIEW_ID;
    }

}
