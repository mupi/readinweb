package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.user.api.User;

import uk.org.ponder.rsf.components.UIBoundString;
import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.tool.SessionBean;
import br.unicamp.iel.tool.commons.CourseComponents;
import br.unicamp.iel.tool.viewparameters.ExerciseViewParameters;

public class ExerciciosProducer implements ViewComponentProducer, 
    ViewParamsReporter {

    private static Log logger = LogFactory.getLog(ExerciciosProducer.class);
    @Setter
    private SessionBean session;
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
        ExerciseViewParameters parameters = 
                (ExerciseViewParameters) viewparams;

        Course course;
        Activity activity;
        Module module;

        if(logic == null){
            System.out.println("Logic bean is null");
            return;
        } else {
            activity = logic.getActivity(parameters.activity);
            module = logic.getModule(parameters.module);
            course = logic.getCourse(parameters.course);
        }
        
        CourseComponents.loadMenu(parameters, tofill);
        CourseComponents.createModulesMenu(tofill, course, this.getViewID(), logic);

        // Breadcrumb
        UIOutput.make(tofill, "current_mod",
                Long.toString(module.getPosition()));
        UIOutput.make(tofill, "current_act",
                Long.toString(activity.getPosition()));
        UIOutput.make(tofill, "current_title", activity.getTitle());

        registerUserAccess(logic.getUserId(), module.getId(), 
                activity.getId());


        UIBranchContainer ui_bc = UIBranchContainer.make(tofill, "ul-exercicios:");
        List<Exercise> exercises = new ArrayList<Exercise>(logic.getExercises(activity));
        for(Exercise e : exercises){
            UIBranchContainer rowMod = UIBranchContainer.make(ui_bc, 
                    "li-linkExer:",
                    Long.toString(e.getId()));

            ExerciseViewParameters evp = 
                    new ExerciseViewParameters(parameters.course, 
                            parameters.module, parameters.activity, e.getId());
            evp.viewID = this.getViewID();

            UIInternalLink link = UIInternalLink.make(rowMod, "input_link_e_", 
                    new UIBoundString("Exerc\u00edcio " + e.getPosition()), evp);

            link.updateFullID("input_link_e_" + e.getId());
        }

        UIForm form_tst_m = UIForm.make(tofill, "input_form_tst_m");

        UIVerbatim.make(tofill, "titulo_exercicio_div", "Exerc\u00edcio");


        String str_fileLoc = this.getExercicioFileLocation(module.getId(), 
                activity.getId(), 1);
        try {
            exerciseString = ""; // FIXMEReadInWebUtilBean.readFileAsString(str_fileLoc);
        } catch (Exception e) { //catch (IOException e) {
            exerciseString = "Ocorreu um erro no momento da leitura do arquivo referente ao exerc\u00edcio.";
            logger.warn(e.getMessage());
        }

        UIVerbatim.make(tofill, "html_exercicio_div", exerciseString);

    }

    /**
     *
     * @param fileName
     * @return
     */
    private String getExercicioFileLocation(long int_moduloIdArg, long int_atividadeIdArg, long int_exercicioIdArg) {
        String retorno;
        String str_fileName;

        str_fileName = "m" + int_moduloIdArg + "a" + int_atividadeIdArg + "e" + int_exercicioIdArg + ".html";

        //retorno = CommonMethods.makeContentDir() + "/modulos/exercicios/" + str_fileName;
        retorno = "/modulos/exercicios/" + str_fileName;
        return (retorno);
    }

    /**
     * this function registers that user accessed this exercise
     */
    private void registerUserAccess(String str_userIdArg, Long module, Long activity) {
        // local variables
        Activity riw_currentActivity;

        User riw_currentUser = this.logic.getCurrentUser();

        // getting objects from database
        riw_currentActivity = this.logic.getActivity(activity);

        if (riw_currentUser == null) {
            // must have an user
            System.out.println("Usuario '" + str_userIdArg + "' nao encontrado.");
            return;
        } else if (riw_currentActivity == null) {
            // must have an activity
            System.out.println("Atividade '" + activity + "' do modulo '" + module + "' nao encontrado.");
            return;
        } else {
            // everything is ok. Register.
            this.logic.controlExercise(riw_currentUser, riw_currentActivity);
            return;
        }
    }

    @Override
    public ViewParameters getViewParameters() {
        return new ExerciseViewParameters(this.getViewID());
    }
}
