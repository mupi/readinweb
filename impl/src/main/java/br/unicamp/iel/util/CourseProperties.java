package br.unicamp.iel.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;

public class CourseProperties {
	private JsonObject courseProperties;

	public CourseProperties(JsonObject courseProperties) {
		this.courseProperties = courseProperties;
	}

	public Long[] getPublishedModulesIds() {
		System.out.println("Published Modules: ");
		System.out.println(courseProperties.toString());
		JsonObject modules = courseProperties.get("modules").asObject();
		ArrayList<Long> ids = new ArrayList<Long>();

		Iterator<Member> it = modules.iterator();
		while (it.hasNext()) {
			Member m = it.next();
			JsonObject module = m.getValue().asObject();
			if (module.get("status").asBoolean()) {
				System.out.println("Module published: " + module.get("status"));
				ids.add(Long.parseLong(m.getName()));
			}
		}

		return Arrays.copyOf(ids.toArray(), ids.size(), Long[].class);
	}

	public Long[] getPublishedActivitiesIds(Long module) {
		JsonObject activities = courseProperties.get("modules").asObject()
				.get(Long.toString(module)).asObject().get("activities")
				.asObject();
		ArrayList<Long> ids = new ArrayList<Long>();

		Iterator<Member> it = activities.iterator();
		while (it.hasNext()) {
			Member m = it.next();
			JsonObject activity = m.getValue().asObject();
			if (activity.get("status").asBoolean()) {
				ids.add(Long.parseLong(m.getName()));
			}
		}

		return Arrays.copyOf(ids.toArray(), ids.size(), Long[].class);
	}

	public Long[] getAllPublishedActivities() {
		JsonObject modules = courseProperties.get("modules").asObject();
		ArrayList<Long> ids = new ArrayList<Long>();

		Iterator<Member> itM = modules.iterator();
		while (itM.hasNext()) {
			Member m = itM.next();
			JsonObject module = m.getValue().asObject();
			if (module.get("status").asBoolean()) { // Verify activities
				JsonObject activities = module.get("activities").asObject();
				Iterator<Member> itA = activities.iterator();
				while (itA.hasNext()) {
					Member a = itA.next();
					JsonObject activity = a.getValue().asObject();
					if (activity.get("status").asBoolean()) {
						ids.add(Long.parseLong(a.getName()));
					}
				}
			}
		}
		return Arrays.copyOf(ids.toArray(), ids.size(), Long[].class);
	}

	public void publishModule(Long module) {
		JsonObject j_module = courseProperties.get("modules").asObject()
				.get(Long.toString(module)).asObject();

		j_module.set("status", true);
	}

	public void publishActivity(Long module, Long activity) {
		JsonObject j_activity = courseProperties.get("modules").asObject()
				.get(Long.toString(module)).asObject().get("activities")
				.asObject().get(Long.toString(activity)).asObject();

		j_activity.set("status", true);
	}

	public void publishNextActivities() {
		int pub = 0;
		JsonObject modules = courseProperties.get("modules").asObject();
		Iterator<Member> itM = modules.iterator();
		while (itM.hasNext() && pub < 2) {
			Member m = itM.next();
			JsonObject module = m.getValue().asObject();
			if (!module.get("status").asBoolean()) {
				module.set("status", true);
			}
			JsonObject activities = module.get("activities").asObject();
			Iterator<Member> itA = activities.iterator();
			while (itA.hasNext() && pub < 2) {
				Member a = itA.next();
				JsonObject activity = a.getValue().asObject();
				if (!activity.get("status").asBoolean()) {
					activity.set("status", true);
					pub++;
				}
			}
		}
	}

	@Override
	public String toString() {
		return courseProperties.toString();
	}

	public boolean isActivityPublished(Long module, Long activity) {
		JsonObject j_activity = courseProperties.get("modules").asObject()
				.get(Long.toString(module)).asObject().get("activities")
				.asObject().get(Long.toString(activity)).asObject();

		return j_activity.get("status").asBoolean();
	}

	public boolean isModulePublished(Long module) {
		JsonObject j_module = courseProperties.get("modules").asObject()
				.get(Long.toString(module)).asObject();

		return j_module.get("status").asBoolean();
	}

	public Long countPublishedActivities() {
		return new Long(getAllPublishedActivities().length);
	}

}
