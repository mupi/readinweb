package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.user.api.User;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.decorators.UIStyleDecorator;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebClassManagementLogic;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.tool.components.CourseComponents;
import br.unicamp.iel.tool.components.GatewayMenuComponent;
import br.unicamp.iel.tool.viewparameters.StudentViewParameters;

/**
 *
 * @author Virgilio Santos
 *
 */

public class ClassProducer implements ViewComponentProducer, 
	NavigationCaseReporter {

    private static Log logger = LogFactory.getLog(ClassProducer.class);

    @Setter
    private ReadInWebCourseLogic logic;
    
    @Setter
    private ReadInWebClassManagementLogic classLogic;

    public static final String VIEW_ID = "turma";

    @Override
    public String getViewID() {
        return VIEW_ID;
    } // end getViewID()

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {
    	
        Course course = logic.getCourse(logic.getCourseId());

        
        GatewayMenuComponent menu = new GatewayMenuComponent(viewparams, 
        		logic.isUserTeacher());
        menu.make(UIBranchContainer.make(tofill, "gateway_menu_replace:"));

        UIOutput.make(tofill, "course_name", course.getTitle());

        Site riwClass = logic.getCurrentSite();
        
        UIOutput.make(tofill, "riw_class_name", riwClass.getTitle());

        UIOutput.make(tofill, "riw_startdate",
                classLogic.getStartDate(riwClass).toString());

        UIOutput.make(tofill, "riw_students_count",
                Long.toString(classLogic.countStudents(riwClass)));

        UIOutput.make(tofill, "riw_activities_published",
                Long.toString(classLogic.countPublishedActivities(riwClass)));

        ArrayList<User> riwStudents =
                new ArrayList<User>(classLogic.getStudents(riwClass));
        UIBranchContainer studentsTable =
                UIBranchContainer.make(tofill, "riw_students:");


        UIForm classState = UIForm.make(tofill, "form_class_state");
        Boolean state = classLogic.getReadInWebClassState(riwClass);

        classState.parameters.add(
                new UIELBinding("#{ManageClassBean.riwClass}",
                        riwClass.getId()));

        classState.parameters.add(
                new UIELBinding("#{ManageClassBean.classState}",
                        Boolean.toString(!state)));
        if(state){ // render pause
            UICommand.make(classState, "pause_class",
                    "#{ManageClassBean.changeClassState}");
        } else { // render play
            UICommand.make(classState, "play_class",
                    "#{ManageClassBean.changeClassState}");
        }

        for(User u : riwStudents){
            UIBranchContainer studentRow =
                    UIBranchContainer.make(studentsTable, "riw_student:");

            if(classLogic.isUserBlocked(u, riwClass)){
                studentRow.decorate((new UIStyleDecorator("danger")));
            }

            UIInternalLink.make(studentRow, "student_name", u.getDisplayName(), 
            		new StudentViewParameters(u.getId()));
            
            //UIOutput.make(studentRow, "student_name", u.getDisplayName());

            UIOutput.make(studentRow, "student_isblocked",
                    classLogic.isUserBlocked(u, riwClass) ? "Sim" : "Não");

            UIOutput.make(studentRow, "student_blocks",
                    Integer.toString(classLogic.getUserBlocks(u, 
                    		riwClass.getId())));
            
            UIOutput.make(studentRow, "student_completed", 
            		Long.toString(classLogic.countActivities(u, riwClass)));

        }
    }

    @Override
    public List<NavigationCase> reportNavigationCases() {
        List<NavigationCase> l = new ArrayList<NavigationCase>();
        return l;
    }
}