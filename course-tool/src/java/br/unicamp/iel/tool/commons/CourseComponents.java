package br.unicamp.iel.tool.commons;

import java.util.ArrayList;
import java.util.List;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.tool.producers.CourseViewParameters;
import br.unicamp.iel.tool.producers.EstrategiaProducer;
import br.unicamp.iel.tool.producers.ExerciciosProducer;
import br.unicamp.iel.tool.producers.ExerciseViewParameters;
import br.unicamp.iel.tool.producers.GramaticaProducer;
import br.unicamp.iel.tool.producers.PrinterTextProducer;
import br.unicamp.iel.tool.producers.SummaryProducer;
import br.unicamp.iel.tool.producers.TextProducer;

public class CourseComponents {
    
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

        cvpLink.viewID = PrinterTextProducer.VIEW_ID;
        UIInternalLink.make(tofill, "lnk_print", cvpLink);
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

        List<Module> modules = new ArrayList<Module>(logic.getModules(course));
        for(Module m : modules){
            List<Activity> activities = new ArrayList<Activity>(logic.getActivities(m));
            
            UIBranchContainer ui_modules = 
                    UIBranchContainer.make(tofill, "li-rowsMod:");
            UIOutput.make(ui_modules, "lnk_modulo", 
                    ("Módulo " + m.getPosition()));
            
            UIBranchContainer ui_activities = 
                    UIBranchContainer.make(ui_modules, "div_atividades:");
            for(Activity a : activities){
                UIBranchContainer row = 
                        UIBranchContainer.make(ui_activities, "p-rowsAct:");
                CourseViewParameters cvp = new CourseViewParameters(course.getId(),
                        m.getId(), a.getId());
                cvp.viewID = viewID;
                UIInternalLink.make(row, "input_link_a_", 
                        a.getPosition() + " - " + a.getTitle(), cvp)
                        .updateFullID("input_link_a_" + a.getId());
            }
        }
    }
    
}
