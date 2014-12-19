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
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.components.decorators.UIStyleDecorator;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Justification;
import br.unicamp.iel.model.JustificationMessage;
import br.unicamp.iel.model.types.JustificationStateTypes;

/**
 *
 * @author Andre Zanchetta
 * @author Virgilio Santos
 *
 */
public class JustificationProducer implements ViewComponentProducer {

	public static final String VIEW_ID = "minhas-justificativas";

	private static Log logger = LogFactory.getLog(JustificationProducer.class);

	@Setter
	private ReadInWebCourseLogic logic;

	@Override
	public String getViewID() {
		return VIEW_ID;
	}


	@Override
	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {

		UIInternalLink.make(tofill, "link_home",
				new SimpleViewParameters(SummaryProducer.VIEW_ID));
		UIInternalLink.make(tofill, "link_justification", viewparams);

		User user = logic.getUser();

		/*UIBranchContainer userAlert =
                UIBranchContainer.make(tofill, "user_alert_message:");
        UIOutput.make(userAlert, "user", user.getDisplayName());

        List<Justification> justifications = logic.getUserJustifications();
        if(logic.isUserBlocked()) {
            userAlert.decorate(new UIStyleDecorator("alert alert-danger"));
            String message =
                    "o seu acesso está bloqueado pois você não completou pelo menos"
                    + " cinco atividades no tempo correto. Para liberar"
                    + " o curso novamente você precisa enviar uma justificativa"
                    + " pelo atraso que será avaliada pela equipe docente.";
            UIOutput.make(userAlert, "message", message);

            if(!justifications.isEmpty()){ // Not empty?
                Justification j = justifications.get(0); // Get first
                if(logic.isActiveJustification(j)){ // Test if is active
                    justifications.remove(0); // if it is, remove it from list

                    UIBranchContainer addMessage =
                            UIBranchContainer.make(tofill, "add_message:");
                    UIOutput.make(addMessage, "current_justification",
                            j.getExplanation());
                    List<JustificationMessage> messages =
                            logic.getJustificationMessages(j);
                    for(JustificationMessage jm : messages){
                    	UIBranchContainer msgsContainer = 
                    			UIBranchContainer.make(addMessage, 
                    					"current_justification_message:");
                        UIOutput.make(msgsContainer, 
                        		"current_justification_sender", 
                        		user.getId().equals(jm.getUser()) ? 
                        				user.getFirstName() : "  :");
                        UIOutput.make(msgsContainer, 
                        		"current_justification_body", jm.getMessage());
                        UIOutput.make(msgsContainer, 
                        		"current_justification_date", 
                        		jm.getDateSent().toString());
                    }

                    UIBranchContainer messageContainer = UIBranchContainer
                            .make(addMessage, "send_message_item:");
                    UIForm messageForm =
                            UIForm.make(messageContainer, "message_form");

                    messageForm.parameters.add(
                            new UIELBinding("#{JustificationBean.justificationId}",
                                    j.getId()));

                    UIOutput.make(messageForm, "message_user", user.getFirstName());

                    UIInput.make(messageForm, "message",
                            "#{JustificationBean.message}");

                    UICommand.make(messageForm, "send_message",
                            "#{JustificationBean.sendMessage}");
                } else { // if not, print the form
                    buildJustificationForm(tofill);
                }
            } else {
                buildJustificationForm(tofill);
            }
        } else {
            String message = "Sem atrasos ou justificativas pendentes!";
            userAlert.decorate(new UIStyleDecorator("alert alert-success"));
            UIOutput.make(userAlert, "message", message);
        }*/
		List<Justification> justifications = logic.getUserJustifications();
		Justification firstJustification = null;
		boolean isActiveJustification = false;
		if(!justifications.isEmpty()){ // Not empty?
			firstJustification = justifications.get(0); // Get first
			isActiveJustification = logic.isActiveJustification(firstJustification);
		}
		boolean isUserBlocked = logic.isUserBlocked();

		if(!isActiveJustification){ // Não tem justificativa ativa 
			UIBranchContainer justficationContainer = 
					UIBranchContainer.make(tofill, "justification_container:");
			
			UIBranchContainer userAlert =
					UIBranchContainer.make(justficationContainer, 
							"user_alert_message:");
			UIOutput.make(userAlert, "user", user.getDisplayName());
			String message = "";
			if(isUserBlocked){ // build form
				message =
						"o seu acesso está bloqueado pois você não completou "
						+ "pelo menos cinco atividades no tempo correto. Para "
						+ "liberar o curso novamente você precisa enviar uma "
						+ "justificativa pelo atraso que será avaliada pela "
						+ "equipe docente.";
				userAlert.decorate(new UIStyleDecorator("alert alert-danger"));
				buildJustificationForm(justficationContainer);
			} else {
				message = "Sem atrasos ou justificativas pendentes!";
	            userAlert.decorate(new UIStyleDecorator("alert alert-success"));
			}
			UIOutput.make(userAlert, "message", message);
		} else if(isUserBlocked) {
			justifications.remove(0); // if it is, remove it from list
			
			UIBranchContainer addMessage =
					UIBranchContainer.make(tofill, "add_message:");
			UIOutput.make(addMessage, "current_justification",
					firstJustification.getExplanation());
			List<JustificationMessage> messages =
					logic.getJustificationMessages(firstJustification);
			for(JustificationMessage jm : messages){
				UIBranchContainer msgsContainer = 
						UIBranchContainer.make(addMessage, 
								"current_justification_message:");
				UIOutput.make(msgsContainer, 
						"current_justification_sender", 
						user.getId().equals(jm.getUser()) ? 
								user.getFirstName() : "  :");
				UIOutput.make(msgsContainer, 
						"current_justification_body", jm.getMessage());
				UIOutput.make(msgsContainer, 
						"current_justification_date", 
						jm.getDateSent().toString());
			}

			UIBranchContainer messageContainer = UIBranchContainer
					.make(addMessage, "send_message_item:");
			UIForm messageForm =
					UIForm.make(messageContainer, "message_form");

			messageForm.parameters.add(
					new UIELBinding("#{JustificationBean.justificationId}",
							firstJustification.getId()));

			UIOutput.make(messageForm, "message_user", user.getFirstName());

			UIInput.make(messageForm, "message",
					"#{JustificationBean.message}");

			UICommand.make(messageForm, "send_message",
					"#{JustificationBean.sendMessage}");
		}

		for(Justification j : justifications){
			UIBranchContainer old_justifications =
					UIBranchContainer.make(tofill, "old_justifications:");
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			UIOutput.make(old_justifications, "old_sent_date",
					df.format(j.getSentDate()));
			if(j.getEvaluatedDate() != null){
				UIOutput.make(old_justifications, "old_evaluated_date",
						df.format(j.getEvaluatedDate()));
			}
			UIOutput.make(old_justifications, "old_explanation",
					j.getExplanation());

			List<JustificationMessage> messages =
					logic.getJustificationMessages(j);
			for(JustificationMessage jm : messages){
				UIOutput.make(old_justifications, "old_justification_message:",
						jm.getMessage());
			}
		}

	}

	private void buildJustificationForm(UIContainer tofill){
		UIForm justificationForm = UIForm.make(
				UIBranchContainer.make(tofill,
						"justification_form_container:"),
				"justification_form");
		UIInput.make(justificationForm, "explanation",
				"#{JustificationBean.explanation}");
		UICommand.make(justificationForm, "send_justification",
				"#{JustificationBean.sendJustification}");
	}
}