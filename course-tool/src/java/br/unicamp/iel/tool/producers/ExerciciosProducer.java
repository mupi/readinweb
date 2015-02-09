package br.unicamp.iel.tool.producers;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIBoundString;
import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.components.decorators.UIStyleDecorator;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.types.AccessTypes;
import br.unicamp.iel.tool.components.CourseComponents;
import br.unicamp.iel.tool.viewparameters.ExerciseViewParameters;

public class ExerciciosProducer implements ViewComponentProducer,
		ViewParamsReporter {

	private static Log logger = LogFactory.getLog(ExerciciosProducer.class);
	@Setter
	private ReadInWebCourseLogic logic;

	public static final String VIEW_ID = "exercicios";

	@Override
	public String getViewID() {
		return VIEW_ID;
	}

	@Override
	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {
		String exerciseString;
		ExerciseViewParameters parameters = (ExerciseViewParameters) viewparams;

		Course course = logic.getCourse(parameters.course);
		Activity activity;
		Module module;
		Exercise exercise;

		if (course == null) {
			System.out.println("Course is null");
			return;
		} else {
			activity = logic.getActivity(parameters.activity);
			module = logic.getModule(parameters.module);
			if (parameters.exercise != null) {
				exercise = logic.getExercise(parameters.exercise);
			} else {
				exercise = logic.getExercise(1L);
			}
		}

		CourseComponents.loadMenu(parameters, tofill);
		CourseComponents.createModulesMenu(tofill, course, this.getViewID(),
				logic);
		CourseComponents.createBreadCrumb(tofill, module, activity);

		logic.registerAccess(AccessTypes.EXERCISE.getTitle(), this.getViewID(),
				activity);

		UIBranchContainer ui_bc = UIBranchContainer.make(tofill,
				"ul-exercicios:");
		List<Exercise> exercises = new ArrayList<Exercise>(
				logic.getExercises(activity));
		for (Exercise e : exercises) {
			UIBranchContainer rowMod = UIBranchContainer.make(ui_bc,
					"li-linkExer:", Long.toString(e.getId()));
			if (e.getId().equals(exercise.getId())) {
				rowMod.decorate(new UIStyleDecorator("active"));
			}

			ExerciseViewParameters evp = new ExerciseViewParameters(
					parameters.course, parameters.module, parameters.activity,
					e.getId());
			evp.viewID = this.getViewID();

			UIInternalLink link = UIInternalLink
					.make(rowMod, "input_link_e_", new UIBoundString(
							"Exerc\u00edcio " + e.getPosition()), evp);

			link.updateFullID("input_link_e_" + e.getId());
		}

		UIForm form_tst_m = UIForm.make(tofill, "input_form_tst_m");
		UIVerbatim.make(tofill, "titulo_exercicio_div", "Exerc\u00edcio");

		// String fileLocation = getExercicioFileLocation(module.getPosition(),
		// activity.getPosition(), exercise.getPosition());

		String fileLocation = exercise.getExercise_path() + File.separator
				+ "index.html";
		try {
			System.out.println(fileLocation);
			exerciseString = CourseComponents.readFile(fileLocation,
					StandardCharsets.UTF_8);
		} catch (Exception e) { // catch (IOException e) {
			exerciseString = "Ocorreu um erro no momento da leitura do arquivo referente ao exerc\u00edcio.";
			logger.warn(e.getMessage());
		}

		UIVerbatim.make(tofill, "html_exercicio_div", exerciseString);
		logic.registerExercisesAccess(activity.getId());
	}

	/**
	 *
	 * @param fileName
	 * @return
	 */
	@Deprecated
	private String getExercicioFileLocation(Integer modulo, Integer atividade,
			Integer exercicio) {
		String retorno;
		String str_fileName;

		str_fileName = "m" + modulo + "a" + atividade + "e" + exercicio
				+ ".html";

		retorno = makeContentDir() + "/modulos/exercicios/" + str_fileName;
		return (retorno);
	}

	/**
	 *
	 * @param fileName
	 * @return
	 */
	private String getExercicioLocation() {
		return "readinweb-uploads/english/exercises";
	}

	public static String makeContentDir() {
		String userDir;

		userDir = System.getProperty("user.dir");
		if (!userDir.endsWith("/bin")) {
			return userDir + "/tool/src/webapp/content";
		} else {
			return userDir.substring(0,
					System.getProperty("user.dir").length() - 4)
					+ "/webapps/readinweb-course-tool/content";
		}
	}

	@Override
	public ViewParameters getViewParameters() {
		return new ExerciseViewParameters(this.getViewID());
	}
}
