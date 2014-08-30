package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIBoundString;
import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.DefaultView;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.types.ControlTypes;
import br.unicamp.iel.tool.viewparameters.CourseViewParameters;

/**
 *
 * @author Andre Zanchetta
 * @author Virgilio Santos
 *
 */
public class SummaryProducer implements ViewComponentProducer, DefaultView {

    public static final String VIEW_ID = "summary";

    private static Log logger = LogFactory.getLog(SummaryProducer.class);

    @Setter
    private ReadInWebCourseLogic logic;

    @Override
    public String getViewID() {
        return VIEW_ID;
    }

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {
        Long course = logic.getCourseId();
        UIInternalLink.make(tofill, "link_home", viewparams);
        UIInternalLink.make(tofill, "link_justification",
                new SimpleViewParameters(JustificationProducer.VIEW_ID));

        if(logic.blockUser()){
            UIBranchContainer li = UIBranchContainer.make(tofill,
                    "user_blocked:");
            UIInternalLink.make(li, "user_blocked_link", "Justificativas",
                    new SimpleViewParameters(JustificationProducer.VIEW_ID));
        } else {
            List<Module> modules =
                    logic.getPusblishedModules(logic.getCourse(course));
            // Fill form with modules
            for(Module m : modules){
                // Get activities
                ArrayList<Activity> activities =
                        new ArrayList<Activity>(
                                logic.getPusblishedActivities(m));
                UIBranchContainer container = createModuleLink(tofill,
                        viewparams, checker, m);
                for(Activity a : activities){
                    // Create activity links
                    createActivityLinks(container, viewparams, checker, a,
                            course, logic.getActivityControlSum(a.getId()));
                }
            }
        }
    }

    private UIBranchContainer createModuleLink(UIContainer tofill,
            ViewParameters viewparams, ComponentChecker checker,
            Module module) {

        // Create 'li' line that will be 'repeated' during module
        // Creation iteration
        UIBranchContainer ui_bc = UIBranchContainer.make(tofill,
                "li_rowsMod:", Long.toString(module.getId()));
        UIInternalLink.make(ui_bc, "lnk_modulo",
                "M\u00f3dulo " + module.getPosition(), "#");

        // Create activity listing base
        UIBranchContainer container = UIBranchContainer.make(ui_bc,
                "ul_atividades:", "");
        container.updateFullID("ul_atividades_" + module.getId());

        return container;
    }

    private void createActivityLinks (UIContainer tofill,
            ViewParameters viewparams, ComponentChecker checker,
            Activity activity, Long course, Integer controlSum){

        // Creates 'li' line for current activity
        UIBranchContainer ui_bc = UIBranchContainer.make(tofill,
                "li_rowsAct:",
                Long.toString(activity.getId()));

        // Changing special characters from the title
        // FIXME Replace this weird comparison
        String title = activity.getPosition() + " - " + activity.getTitle();
        if (title.contains("&#8242;") || title.contains("&egrave;")) {
            title = title.replace("&#8242;", "'");
            title = title.replace("&egrave;", "è");
        }

        CourseViewParameters cvp = new CourseViewParameters();
        cvp.viewID = TextProducer.VIEW_ID;
        cvp.activity = activity.getId();
        cvp.module = activity.getModule().getId();
        cvp.course = course;

        UIInternalLink.make(ui_bc, "but_linkA_", new UIBoundString(title),
                cvp);

        // FIXME links are dumb here
        String tick = "<img src='../../../readinweb-course-tool/content/imagens/tick-icon.png'>";
        String delete = "<img src='../../../readinweb-course-tool/content/imagens/delete-icon.png'>";

        // Show if the text was read
        if (ControlTypes.hasDone(ControlTypes.TEXT, controlSum)) {
            UIVerbatim.make(ui_bc, "txt_textRead_", tick);
        } else {
            UIVerbatim.make(ui_bc, "txt_textRead_", delete);
        }

        // Show if the questions were made
        UIVerbatim ui_v;
        if (ControlTypes.hasDone(ControlTypes.QUESTIONS, controlSum)) {
            ui_v = UIVerbatim.make(ui_bc, "txt_questionMade_", tick);
        } else {
            ui_v = UIVerbatim.make(ui_bc, "txt_questionMade_", delete);
        }
        ui_v.updateFullID("questions_" + activity.getModule().getId()
                + "_" + activity.getId());

        // Show if exercises were visualized
        if (ControlTypes.hasDone(ControlTypes.EXERCISE, controlSum)) {
            UIVerbatim.make(ui_bc, "txt_exercMade_", tick);
        } else {
            UIVerbatim.make(ui_bc, "txt_exercMade_", delete);
        }
    }
}