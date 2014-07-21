/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unicamp.iel.logic;

import java.util.List;

import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.CourseData;

/**
 *
 * @author vsantos
 */
public interface ReadInWebAminLogic {
    public enum ContentType {
        activity,
        words,
        glossary,
        question,
        exercise,
    }

    public List<Course> getCourses();

    public boolean createCourse(Course c);

    public boolean sendContent(String data, ContentType ct);

    public boolean editContent(String data, ContentType ct, Activity a);

    public boolean deleteActivity(Long activityId);

	public CourseData getCourseData();

}
