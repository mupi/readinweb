package br.unicamp.iel.tool.producers;

import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UISelect;
import uk.org.ponder.rsf.state.scope.BeanDestroyer;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebClassManagementLogic;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.tool.CreateClassBean;
import br.unicamp.iel.tool.commons.ManagerComponents;

/**
  *
 * @author Virgilio Santos
 *
 */

public class CreateClassProducer implements ViewComponentProducer {

    private static Log logger = LogFactory.getLog(CreateClassProducer.class);

    @Setter
    private ReadInWebClassManagementLogic logic;

    @Setter
    private UserDirectoryService userDirectoryService;

    @Setter
    private CreateClassBean createClassBean;

    @Setter
    private BeanDestroyer destroyer;

    public static final String VIEW_ID = "criar_turma";

    @Override
    public String getViewID() {
        return VIEW_ID;
    } // end getViewID()

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {

        ManagerComponents.loadMenu(viewparams, tofill);
        Course course = logic.getCourse(logic.getManagerCourseId());
        UIOutput.make(tofill, "riw_course_title", course.getTitle());
        /**
         *
         * title
         * type: course
         * descricao
         * published
         * joinable : true
         * role : Student
         * readinwebcourse : this
         * readinwebcourse.data : generate
         *
         */

        if(createClassBean.dataSent()){
            createClassBean.createClass(course);
            destroyer.destroy();
        } else {
            // Render form
            UIForm form = UIForm.make(tofill, "add_riw_class");
            UIInput.make(form, "title", "#{CreateClassBean.title}");
            UIInput.make(form, "type", "#{CreateClassBean.type}");
            UIInput.make(form, "description", "#{CreateClassBean.description}");

            List<User> teachers = logic.getTeacherList();

            String[] teacherOptions = new String[teachers.size()];
            String[] teacherLabels = new String[teachers.size()];
            for(int i = 0; i < teachers.size(); i++){
                System.out.println(teachers.get(i).getDisplayName());
                teacherOptions[i] = teachers.get(i).getId();
                teacherLabels[i] = teachers.get(i).getDisplayName();
            }

            UISelect.make(form, "teacher", teacherOptions, teacherLabels,
                    "#{CreateClassBean.teacherUserId}");

            UIInput.make(form, "weekly_activities",
                    "#{CreateClassBean.weeklyActivities}").setValue(Integer.toString(2));
            UIInput.make(form, "published", "#{CreateClassBean.published}");
            UIInput.make(form, "start_date", "#{CreateClassBean.startDate}");
        }

    }

}