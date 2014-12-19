package br.unicamp.iel.tool;

import org.sakaiproject.site.api.Site;

import lombok.Data;
import lombok.Setter;
import br.unicamp.iel.logic.ReadInWebClassManagementLogic;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Property;

@Data
public class CreateClassBean {
    private String title;
    private String type;
    private String description;
    private String teacherUserId;
    private Integer weeklyActivities;
    private Boolean published;
    private String startDate;

    @Setter
    private ReadInWebClassManagementLogic logic;

    public boolean createClass(Course course){
        try {
            Site site = logic.createClass(course, title);

            if(title != null){
                site.setTitle(title);
            }
            if(description != null){
                site.setDescription(description);
            }
            if(teacherUserId != null){
                site.addMember(teacherUserId, "Instructor", true, true);
            }

            site.setPublished(true);

            if(published != null) {
                site.setJoinable(published);
            }

            if(startDate != null){
                logic.addProperty(site,
                        Property.COURSESTARTDATE.getName(), startDate);
            }

            logic.addProperty(site,
                    Property.COURSEREMISSIONTIME.getName(),
                    Integer.toString(2));

            logic.addProperty(site, Property.COURSESTATE.getName(),
                    Boolean.toString(false));

            logic.saveSite(site);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean dataSent() {
        return title != null || type != null || description != null ||
                teacherUserId != null || weeklyActivities != null ||
                startDate != null;
    }
}
