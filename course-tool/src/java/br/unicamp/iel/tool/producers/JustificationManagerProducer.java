package br.unicamp.iel.tool.producers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.user.api.User;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.decorators.UIStyleDecorator;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Justification;
import br.unicamp.iel.model.JustificationMessage;
import br.unicamp.iel.model.types.JustificationStateTypes;
import br.unicamp.iel.tool.components.GatewayMenuComponent;
import br.unicamp.iel.tool.viewparameters.JustificationViewParameters;

/**
 *
 * @author Virgilio Santos
 *
 */
public class JustificationManagerProducer implements ViewComponentProducer, ViewParamsReporter {

	public static final String VIEW_ID = "justificativa-single";

	private static Log logger = LogFactory.getLog(JustificationManagerProducer.class);

	@Setter
	private ReadInWebCourseLogic logic;

	@Override
	public String getViewID() {
		return VIEW_ID;
	}


	@Override
	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {

		boolean isTeacher = logic.isUserTeacher();
		if(!isTeacher){
			return;
		}

		GatewayMenuComponent menu = new GatewayMenuComponent(viewparams, 
				isTeacher);
		menu.make(UIBranchContainer.make(tofill, "gateway_menu_replace:"));

		JustificationViewParameters parameters =
				(JustificationViewParameters) viewparams;

		Justification justification =
				logic.getJustification(parameters.justification);

		User student = logic.getSudent(justification.getUser());

		if(!JustificationStateTypes.isOld(justification.getState())){
			UIBranchContainer container = UIBranchContainer.make(tofill,
					"current_justification_container:");


			UIOutput.make(container, "username", student.getDisplayName());

			// TODO refuse acept form
			//        current_evaluate_form
			UIForm evaluateForm = UIForm.make(container, "current_evaluate_form");
			evaluateForm.parameters.add(
					new UIELBinding("#{JustificationBean.justificationId}",
							justification.getId()));
			//        current_refuse
			UICommand.make(evaluateForm, "current_refuse",
					"#{JustificationBean.refuseJustification}");
			//        current_accept
			UICommand.make(evaluateForm, "current_accept",
					"#{JustificationBean.acceptJustification}");

			UIOutput.make(container, "current_justification_explanation",
					justification.getExplanation());

			UIOutput.make(container, "sent_date",
					justification.getSentDate().toString());

			UIOutput evaluatedDate = UIOutput.make(container, "evaluated_date");

			evaluatedDate.setValue(justification.getEvaluatedDate() == null ?
					"Ainda não avaliado" :
						justification.getEvaluatedDate().toString());

			List<JustificationMessage> messages =
					logic.getJustificationMessages(justification);

			for(JustificationMessage jm : messages){
				UIBranchContainer currentMessage = UIBranchContainer.make(tofill,
						"current_justification_message:");
				UIOutput messageUser = UIOutput.make(currentMessage, 
						"current_justification_sender",	"Professor");
				if(student.getId().equals(jm.getUser())){
					messageUser.setValue(student.getDisplayName());
				} else {
					currentMessage.decorate(new UIStyleDecorator("alert-warning"));
				}
				UIOutput.make(currentMessage, "current_justification_body", 
						jm.getMessage());
				UIOutput.make(currentMessage, "current_justification_date",
						jm.getDateSent().toString());
			}

			UIBranchContainer messageContainer = UIBranchContainer
					.make(container, "send_message_item:");
			UIForm messageForm =
					UIForm.make(messageContainer, "message_form");

			messageForm.parameters.add(
					new UIELBinding("#{JustificationBean.justificationId}",
							justification.getId()));

			UIOutput.make(messageForm, "message_user", 
					logic.getUser().getFirstName());

			UIInput.make(messageForm, "message",
					"#{JustificationBean.message}");

			UICommand.make(messageForm, "send_message",
					"#{JustificationBean.sendMessage}");

		}

		List<Justification> justifications = 
				logic.getUserJustifications(justification.getUser());

		for(Justification j : justifications){
			if(!JustificationStateTypes.toShow(j.getState())){
				UIBranchContainer old_justifications =
						UIBranchContainer.make(tofill, "old_justifications:");
				if(j.getState() >= 4){
					old_justifications.decorate(new UIStyleDecorator("alert-danger"));
				}
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				UIOutput.make(old_justifications, "old_sent_date",
						df.format(j.getSentDate()));
				if(j.getEvaluatedDate() != null){
					UIOutput.make(old_justifications, "old_evaluated_date",
							df.format(j.getEvaluatedDate()));
				}
				UIOutput.make(old_justifications, "old_explanation",
						j.getExplanation());

				List<JustificationMessage> jmessages =
						logic.getJustificationMessages(j);
				System.out.println("Num mens: " + jmessages.size());
				for(JustificationMessage jm : jmessages){
					UIBranchContainer oldMessage = 
							UIBranchContainer.make(old_justifications,
							"old_justification_message:");
					UIOutput messageUser = UIOutput.make(oldMessage, 
							"old_justification_sender",	"Professor");
					if(student.getId().equals(jm.getUser())){
						messageUser.setValue(student.getDisplayName());
					} else {
						oldMessage.decorate(new UIStyleDecorator("alert-warning"));
					}
					UIOutput.make(oldMessage, "old_justification_body", 
							jm.getMessage());
					UIOutput.make(oldMessage, "old_justification_date",
							jm.getDateSent().toString());
				}
			}
		}
	}

	@Override
	public ViewParameters getViewParameters() {
		return new JustificationViewParameters();
	}
}