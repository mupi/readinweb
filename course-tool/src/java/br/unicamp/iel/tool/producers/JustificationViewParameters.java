package br.unicamp.iel.tool.producers;

import lombok.Data;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;

@Data
public class JustificationViewParameters extends SimpleViewParameters {
    private String str_justification;

    public JustificationViewParameters() {
        this.viewID = SummaryProducer.VIEW_ID;
    }
}
