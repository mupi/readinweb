package br.unicamp.iel.tool.commons;

import java.util.ArrayList;
import java.util.List;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.view.ViewRoot;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.tool.producers.ClassesProducer;
import br.unicamp.iel.tool.producers.JustificationsProducer;
import br.unicamp.iel.tool.viewparameters.ClassesViewParameters;

public class ManagerComponents {

    public static void loadMenu(ViewParameters viewparams, UIContainer tofill){
        ClassesViewParameters cvp = new ClassesViewParameters();
        cvp.viewID = ClassesProducer.VIEW_ID;
        UIInternalLink.make(tofill, "link_turmas", cvp);
//
//        SimpleViewParameters svp = new SimpleViewParameters();
//        svp.viewID = JustificationsProducer.VIEW_ID;
//        UIInternalLink.make(tofill, "link_justificativas", svp);
    }

    /**
     *
     * Module menu
     *
     *   li-rowsMod:
     *       lnk_modulo
     *   div_atividades:
     *       p-rowsAct:
     *           input_link_a_
     *
     * @param tofill
     * @param course
     */
    public static void createModulesMenu(UIContainer tofill, Course course,
            String viewID, ReadInWebAdminLogic logic){

        List<Module> modules = new ArrayList<Module>(logic.getModules(course));
        for(Module m : modules){
            List<Activity> activities =
                    new ArrayList<Activity>(logic.getActivities(m));

            UIBranchContainer ui_modules =
                    UIBranchContainer.make(tofill, "li-rowsMod:");
            UIOutput.make(ui_modules, "lnk_modulo",
                    ("Módulo " + m.getPosition()));

            UIBranchContainer ui_activities =
                    UIBranchContainer.make(ui_modules, "div_atividades:");
            for(Activity a : activities){
                UIBranchContainer row =
                        UIBranchContainer.make(ui_activities, "p-rowsAct:");
                ClassesViewParameters cvp =
                        new ClassesViewParameters();
                cvp.viewID = viewID;
                UIInternalLink.make(row, "input_link_a_",
                        a.getPosition() + " - " + a.getTitle(), cvp)
                        .updateFullID("input_link_a_" + a.getId());
            }
        }
    }

    public static void createBreadCrumb(UIContainer tofill, Activity activity,
        Module module, String viewID) {

        UIOutput.make(tofill, "current_mod",
                Long.toString(module.getPosition()));
        UIOutput.make(tofill, "current_act",
                Long.toString(activity.getPosition()));
        UIOutput.make(tofill, "current_title", activity.getTitle());
    }

    public static void debugIt(UIContainer tofill){
        if (tofill instanceof ViewRoot) {
            ((ViewRoot)tofill).debug = true;
        }
    }
}
