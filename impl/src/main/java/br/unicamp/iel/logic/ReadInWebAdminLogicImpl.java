/**
 * 
 */
package br.unicamp.iel.logic;

import java.util.List;

import lombok.Setter;
import br.unicamp.iel.dao.ReadInWebDao;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.CourseData;

/**
 * @author vsantos
 *
 */
public class ReadInWebAdminLogicImpl implements ReadInWebAminLogic {
	
	@Setter
	private ReadInWebDao dao;
	
	@Setter
	private SakaiProxy sakaiProxy;
		
	/* (non-Javadoc)
	 * @see br.unicamp.iel.logic.ReadInWebAminLogic#getCourses()
	 */
	@Override
	public List<Course> getCourses() {
		// TODO Auto-generated method stub
		
		return null;
	}

	/* (non-Javadoc)
	 * @see br.unicamp.iel.logic.ReadInWebAminLogic#createCourse(br.unicamp.iel.model.Course)
	 */
	@Override
	public boolean createCourse(Course c) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see br.unicamp.iel.logic.ReadInWebAminLogic#getCourseData()
	 */
	@Override
	public CourseData getCourseData() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.unicamp.iel.logic.ReadInWebAminLogic#sendContent(java.lang.String, br.unicamp.iel.logic.ReadInWebAminLogic.ContentType)
	 */
	@Override
	public boolean sendContent(String data, ContentType ct) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see br.unicamp.iel.logic.ReadInWebAminLogic#editContent(java.lang.String, br.unicamp.iel.logic.ReadInWebAminLogic.ContentType, br.unicamp.iel.model.Activity)
	 */
	@Override
	public boolean editContent(String data, ContentType ct, Activity a) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see br.unicamp.iel.logic.ReadInWebAminLogic#deleteActivity(java.lang.Long)
	 */
	@Override
	public boolean deleteActivity(Long activityId) {
		// TODO Auto-generated method stub
		return false;
	}

}
