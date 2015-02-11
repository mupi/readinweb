package br.unicamp.iel.tool;

import lombok.Data;
import lombok.Setter;

import org.sakaiproject.site.api.Site;

import br.unicamp.iel.logic.ReadInWebClassManagementLogic;
import br.unicamp.iel.model.Property;
import br.unicamp.iel.tool.commons.ManagerComponents;

@Data
public class ManageClassBean {
	private String riwClass;
	private Boolean classState;

	@Setter
	private ReadInWebClassManagementLogic logic;

	public String changeClassState() {
		if (changeClassStatedataSent()) {
			logic.setClassState(logic.getReadInWebClass(riwClass), classState);
			return ManagerComponents.MODIFIED;
		} else {
			return ManagerComponents.DATA_EMPTY;
		}
	}

	public boolean changeClassStatedataSent() {
		return classState != null || riwClass != null;
	}

	private String title;
	private String type;
	private String description;
	private String teacherUserId;
	private Integer weeklyActivities;
	private Boolean published;
	private String startDate;
	private Long course;

	public String createReadInWebClass() {
		try {
			Site site = logic.createClass(logic.getCourse(course), title);

			if (title != null) {
				site.setTitle(title);
			}
			if (description != null) {
				site.setDescription(description);
			}
			if (teacherUserId != null) {
				site.addMember(teacherUserId, "Instructor", true, true);
			}

			site.setPublished(true);

			if (published != null) {
				System.out.println("Publish? " + published);
				site.setJoinable(published);
			}

			if (startDate != null) {
				System.out.println("The date to start: " + startDate);
				logic.addProperty(site, Property.COURSESTARTDATE.getName(),
						startDate);
			}

			logic.addProperty(site, Property.COURSEREMISSIONTIME.getName(),
					Integer.toString(2));

			logic.addProperty(site, Property.COURSESTATE.getName(),
					Boolean.toString(false));

			logic.saveSite(site);

			System.out.println("All Ok! =)");
			return ManagerComponents.CREATED;
		} catch (Exception e) {
			e.printStackTrace();
			return ManagerComponents.CREATE_FAIL;
		}
	}

	public boolean createClassdataSent() {
		return title != null || type != null || description != null
				|| teacherUserId != null || weeklyActivities != null
				|| startDate != null;
	}

}
