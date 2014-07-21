package br.unicamp.iel.tool.producers;

import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.tool.SessionBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;

public class PrinterTextProducer implements ViewComponentProducer {

	private static Log logger = LogFactory.getLog(PrinterTextProducer.class);
	private ReadInWebCourseLogic logic;
	private SessionBean session;

	// The VIEW_ID must match the html template (without the .html)
	public static final String VIEW_ID = "printerText";

	public void setSession(SessionBean sessionArg) {
		this.session = sessionArg;
	}

	@Override
	public String getViewID() {
		return VIEW_ID;
	} // end getViewID()

	public void setLogic(ReadInWebCourseLogic logic) {
		this.logic = logic;
	}


	@Override
	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {
		if ((this.session != null)
				&& (this.logic != null)
				&& (this.session.getModule() > 0)
				&& (this.session.getActivity() > 0)) {
			UIVerbatim div_tmp;
			Activity currentActivity;

			if (logic == null) {
				currentActivity = new Activity();
				currentActivity.setText("nulo");
			} else {
				currentActivity = logic.getActivity(session.getActivity());
				if (currentActivity == null) {
					currentActivity = new Activity();
					currentActivity.setTitle("Nenhuma atividade selecionada.");
					currentActivity.setText("\u00c9 preciso selecionar alguma atividade.");
				} else {
					logic.registerAccess("Acesso ao texto.", this.getViewID(), currentActivity);
				}
			}

			if (session != null) {
				UIVerbatim.make(tofill, "header_text", "M&oacute;dulo " 
						+ session.getModule() + ", Atividade " + session.getActivity());
			}

			div_tmp = UIVerbatim.make(tofill, "title_text", currentActivity.getTitle());
			div_tmp.updateFullID("title_printtext");

			if ((currentActivity.getImage() != null) 
					&& (currentActivity.getImage().compareToIgnoreCase("") != 0)) {

				div_tmp = UIVerbatim.make(tofill, "picture_text", 
						this.makeImageURL(currentActivity));
				div_tmp.updateFullID("picture_printtext");
			}

			div_tmp = UIVerbatim.make(tofill, "text_text", 
					currentActivity.getText());
			div_tmp.updateFullID("text_printtext");
		}
	}

	private String makeImageURL(Activity activity) {
		String str_retorno, str_imagem;
		Long int_numAct, int_numMod;

		str_imagem = activity.getImage();
		int_numAct = activity.getId();
		int_numMod = activity.getModule().getId();

		str_retorno = "<img src='/readinweb-tool/content/modulos/modulo" 
		// FIXME		+ ReadInWebUtilBean.makeTwoCharNumber(int_numMod) 
				+ "/atividades/atividade" 
		// FIXME		+ ReadInWebUtilBean.makeTwoCharNumber(int_numAct) 
				+ "/texto/imagens/" + str_imagem + "' id='textactimage' />";

		return (str_retorno);
	}
}
