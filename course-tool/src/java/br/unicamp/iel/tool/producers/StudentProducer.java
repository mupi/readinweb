package br.unicamp.iel.tool.producers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.user.api.User;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebClassManagementLogic;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Justification;
import br.unicamp.iel.model.reports.UserAccess;
import br.unicamp.iel.tool.components.GatewayMenuComponent;
import br.unicamp.iel.tool.viewparameters.StudentViewParameters;

public class StudentProducer implements ViewComponentProducer,
ViewParamsReporter {

	private static Log logger = LogFactory.getLog(StudentProducer.class);
	@Setter
	private ReadInWebCourseLogic logic;

	@Setter
	private ReadInWebClassManagementLogic classLogic;

	public static final String VIEW_ID = "student";

	@Override
	public String getViewID() {
		return VIEW_ID;
	} // end getViewID()

	@Override
	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {

		StudentViewParameters svp = (StudentViewParameters) viewparams;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy à's' HH:mm");
		DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		User student = logic.getSudent(svp.userId);
		Site riwClass = logic.getCurrentSite();

		GatewayMenuComponent menu = new GatewayMenuComponent(viewparams, 
				logic.isUserTeacher());
		menu.make(UIBranchContainer.make(tofill, "gateway_menu_replace:"));		

		UIOutput.make(tofill, "student_name", student.getDisplayName());
		UIOutput.make(tofill, "signup_date", df.format(student.getCreatedDate()));
		//35: TODO put the correct date
		UIOutput.make(tofill, "first_access_date", 
				df.format(student.getCreatedDate()));
		//36: TODO put the correct date
		UIOutput.make(tofill, "last_access_date", 
				df.format(student.getCreatedDate()));

		UIOutput.make(tofill, "activities_done", 
				Long.toString(classLogic.countActivities(student, riwClass)));

		UIOutput.make(tofill, "activities_published", 
				Long.toString(logic.countActivities(riwClass)));

		List<Justification> justifications = 
				logic.getUserJustifications(student);
		for(Justification j : justifications){
			UIBranchContainer blockBox = 
					UIBranchContainer.make(tofill, "block_box:");

			UIOutput.make(blockBox, "block_date", df.format(j.getSentDate()));
			UIOutput.make(blockBox, "evaluate_date", 
					df.format(j.getEvaluatedDate()));
			//51:		acessou o curso novamente dia <span rsf:id="next_access_date"></span>
			UIOutput.make(blockBox, "explanation", j.getExplanation());			
		}

		ArrayList<UserAccess> access = new ArrayList<UserAccess>(
				classLogic.getAccessData(student, riwClass));
		for(UserAccess ua : access){
			UIBranchContainer row = 
					UIBranchContainer.make(tofill, "activity_access_detail:");
			UIOutput.make(row, "activity_first_access", 
					df2.format(ua.getPrimeiro()));
			UIOutput.make(row, "activity_last_access", 
					df2.format(ua.getUltimo()));
			UIOutput.make(row, "activity_title", ua.getAtividade());
			UIOutput.make(row, "activity_total_accesses", 
					ua.getTotal().toString());
		}
		
				
		//92:		<tr rsf:id="activity_completion_order">
		//93:			<td rsf:id="activity">1 - How green is my planet?</td>
		//94:			<td><span rsf:id="activity_completed"

	}

	@Override
	public ViewParameters getViewParameters() {
		return new StudentViewParameters();
	}
}
