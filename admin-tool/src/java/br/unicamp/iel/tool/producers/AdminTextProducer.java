package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UILink;
import uk.org.ponder.rsf.components.UIParameter;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.state.scope.BeanDestroyer;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.tool.AdminActivityBean;
import br.unicamp.iel.tool.AdminQuestionBean;
import br.unicamp.iel.tool.commons.CourseComponents;
import br.unicamp.iel.tool.viewparameters.CourseViewParameters;

/**
 *
 * @author Virgilio Santos
 *
 */
public class AdminTextProducer implements ViewComponentProducer, ViewParamsReporter, NavigationCaseReporter {

    private static Log logger = LogFactory.getLog(AdminTextProducer.class);
    public static final String VIEW_ID = "admin_text";

    @Setter
    private ReadInWebAdminLogic logic;

    @Setter
    AdminQuestionBean questionBean;

    @Setter
    AdminActivityBean activityBean;

    @Setter
    private BeanDestroyer destroyer;

    @Override
    public String getViewID() {
        return VIEW_ID;
    }

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {
        Activity activity;
        Module module;
        Course course;

        CourseViewParameters parameters = (CourseViewParameters) viewparams;

        if(parameters.question != null){ // Question sent
            questionBean.updateQuestion(parameters.question);
            destroyer.destroy();
        } else if(activityBean.activityDataSent()){ // Activity sent
            activityBean.updateActivity(parameters.activity);
            destroyer.destroy();
        }

        if(parameters.module == null) parameters.module = 1L;
        if(parameters.activity == null) parameters.activity = 1L;

        course = logic.getCourse(parameters.course);
        activity = logic.getActivity(parameters.activity);
        module = logic.getModule(parameters.module);

        CourseComponents.checkParameters(course, module, activity);
        CourseComponents.loadMenu(parameters, tofill);
        CourseComponents.createModulesMenu(tofill, course, this.getViewID(), logic);
        CourseComponents.createBreadCrumb(tofill, activity, module, this.getViewID());

        UIForm activity_form = UIForm.make(tofill, "activity_form");

        UICommand.make(activity_form, "send_activity");

        UIInput.make(activity_form, "title_text", "#{AdminActivityBean.title}",
                activity.getTitle());

        UIInput.make(activity_form, "eta_read", "#{AdminActivityBean.etaRead}",
                Integer.toString(activity.getEtaRead()));

        // Pre reading exercise
        UIInput.make(activity_form, "prelecture_text", "#{AdminActivityBean.preReading}",
                activity.getPrereading());

        UIInput.make(activity_form, "text_text", "#{AdminActivityBean.text}", activity.getText());

        List<Question> questions = logic.getQuestions(activity);
        for(Question q : questions){
            UIBranchContainer row = UIBranchContainer.make(tofill, "li-rows:",
                    Integer.toString(q.getPosition()));
            UILink ui_l = UILink.make(row, "input_link_q_",
                    Integer.toString(q.getPosition()),
                    "/#question_" + q.getId());
            ui_l.updateFullID("input_link_q_" + q.getId());

            UIBranchContainer ui_bc = UIBranchContainer.make(tofill,
                    "div_questions:");
            ui_bc.updateFullID("question_" + q.getId());

            UIForm ui_form = UIForm.make(ui_bc, "question_form");
            ui_form.parameters.add(new UIParameter("question",
                    Long.toString(q.getId())));

            UIInput.make(ui_form, "question_position",
                    "#{AdminQuestionBean.position}",
                    Long.toString(q.getPosition()));

            UIInput.make(ui_form, "question", "#{AdminQuestionBean.question}",
                    q.getQuestion());

            UIInput.make(ui_form, "suggested_answer",
                    "AdminQuestionBean.suggestedAnswer",
                    q.getSuggestedAnswer());

            //UICommand.make(ui_form, "send_question", "#{AdminQuestionBean.sendQuestion}");
            UICommand.make(ui_form, "send_question");
        }
    }

    @Override
    public ViewParameters getViewParameters() {
        return new CourseViewParameters(this.getViewID());
    }

    @Override
    public List<NavigationCase> reportNavigationCases() {
        CourseViewParameters cvp = (CourseViewParameters) this.getViewParameters();
        List<NavigationCase> l = new ArrayList<NavigationCase>();
        //l.add(new NavigationCase("added", cvp));
        return l;
    }

}