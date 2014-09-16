package br.unicamp.iel.tool.commons;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import uk.org.ponder.rsf.components.UIAnchor;
import uk.org.ponder.rsf.components.UIBoundString;
import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UILink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.tool.producers.EstrategiaProducer;
import br.unicamp.iel.tool.producers.ExerciciosProducer;
import br.unicamp.iel.tool.producers.GramaticaProducer;
import br.unicamp.iel.tool.producers.SummaryProducer;
import br.unicamp.iel.tool.producers.TextProducer;
import br.unicamp.iel.tool.viewparameters.CourseViewParameters;
import br.unicamp.iel.tool.viewparameters.ExerciseViewParameters;

public class CourseComponents {

    public static final String DATA_SENT = "Data Succesfully sent";
    public static final String DATA_EMPTY = "Data is empty, nothing to save";
    public static final String DATA_SEND_FAILED = "Data sending failed";

    public static void loadMenu(ViewParameters viewparams, UIContainer tofill){
        CourseViewParameters cvpLink;
        ExerciseViewParameters evpLink;
        Long exercise = 1L;

        // Checking view parameters
        if(viewparams instanceof CourseViewParameters){
            CourseViewParameters cvp =
                    (CourseViewParameters) viewparams;

            cvpLink = new CourseViewParameters(cvp.course,
                    cvp.module, cvp.activity);
            evpLink = new ExerciseViewParameters(cvp.course,
                    cvp.module, cvp.activity, exercise);
        } else if(viewparams instanceof ExerciseViewParameters){
            ExerciseViewParameters evp =
                    (ExerciseViewParameters) viewparams;

            cvpLink = new CourseViewParameters(evp.course,
                    evp.module, evp.activity);
            evpLink = new ExerciseViewParameters(evp.course,
                    evp.module, evp.activity,
                    evp.exercise);
        } else {
            return;
        }

        // Menu links

        cvpLink.viewID = TextProducer.VIEW_ID;
        UIInternalLink.make(tofill, "linktext", cvpLink);

        evpLink.viewID = ExerciciosProducer.VIEW_ID;
        UIInternalLink.make(tofill, "linkexercise", evpLink);

        cvpLink.viewID = EstrategiaProducer.VIEW_ID;
        UIInternalLink.make(tofill, "linkstrategy", cvpLink);

        cvpLink.viewID = GramaticaProducer.VIEW_ID;
        UIInternalLink.make(tofill, "linkgrammar", cvpLink);

        cvpLink.viewID = SummaryProducer.VIEW_ID;
        UIInternalLink.make(tofill, "linksumary", cvpLink);
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
            String viewID, ReadInWebCourseLogic logic){

        List<Module> modules = logic.getPublishedModules(course);
        for(Module m : modules){
            List<Activity> activities = logic.getPublishedActivities(m);

            UIBranchContainer ui_modules =
                    UIBranchContainer.make(tofill, "li-rowsMod:");

            UILink.make(ui_modules, "lnk_modulo",
                    new UIBoundString("Módulo " + m.getPosition()),
                    "/#module_" + m.getId() + "_activities");

            UIBranchContainer ui_activities =
                    UIBranchContainer.make(ui_modules, "div_atividades:");
            ui_activities.updateFullID("module_" + m.getId() + "_activities");
            for(Activity a : activities){
                UIBranchContainer row =
                        UIBranchContainer.make(ui_activities, "p-rowsAct:");
                CourseViewParameters cvp =
                        new CourseViewParameters(course.getId(), m.getId(),
                                a.getId());
                cvp.viewID = viewID;
                UIInternalLink.make(row, "input_link_a_",
                        a.getPosition() + " - " + a.getTitle(), cvp)
                        .updateFullID("input_link_a_" + a.getId());
            }
        }
    }

    public static void createBreadCrumb(UIContainer tofill, Module module,
            Activity activity) {
        UIOutput.make(tofill, "current_mod",
                Long.toString(module.getPosition()));
        UIOutput.make(tofill, "current_act",
                Long.toString(activity.getPosition()));
        UIVerbatim.make(tofill, "current_title", activity.getTitle());
    }

    public static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
