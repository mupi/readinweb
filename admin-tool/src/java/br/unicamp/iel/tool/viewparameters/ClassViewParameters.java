package br.unicamp.iel.tool.viewparameters;

import uk.org.ponder.rsf.viewstate.SimpleViewParameters;

public class ClassViewParameters extends SimpleViewParameters {
	public String siteId;
	public Long course;
	public boolean classChanged;
	public boolean classAdded;

	public ClassViewParameters() {
	}

	public ClassViewParameters(String viewID) {
		super();
		this.viewID = viewID;
	}

	public ClassViewParameters(String viewID, boolean classAdded,
			boolean classChanged) {
		super();
		this.viewID = viewID;
		this.classAdded = classAdded;
		this.classChanged = classChanged;
	}
}
