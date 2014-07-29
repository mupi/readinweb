/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unicamp.iel.logic;

import java.util.List;

import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Answer;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.model.Strategy;

/**
 *
 * @author Virgilio Santos
 */
public interface ReadInWebCommonLogic {

    public void loadInitialCSVData();

    public Long[] getListIds(List<?> list);

    public String getUserId();

    public Course getCourse(Long course);

    public Module getModule(Long module);

    public Activity getActivity(Long activity);

    public Question getQuestion(Long question);

    public Long countUserAnswers(String user, Long[] ids);

    public Answer getStudentAnswer(Long question);

    public List<Course> getCourses();

    public List<Module> getModules(Course course);

    public List<Activity> getActivities(Module module);

    public List<FunctionalWord> getFunctionalWord(Course course);

    public List<DictionaryWord> getDictionary(Activity activity);

    public List<Strategy> getStrategies(Activity activity);

    public List<Question> getQuestions(Activity activity);

    public List<Exercise> getExercises(Activity activity);

    public String getCurrentSiteId();

    public void saveCourse(Course c);

    public void saveModule(Module m);

    public void saveActivity(Activity a);

    public void saveDictionaryWord(DictionaryWord dw);

    public void saveExercise(Exercise e);

    public void saveQuestion(Question q);

    public void saveFunctionalWord(FunctionalWord fw);

    public void saveStrategy(Strategy strategy);

    public String getDefaultCoursePropertyString(Course course);

    public String getDefaultUserPropertyString();

    public String userPropertySkelString(String siteId);

    public String getCoursePropertyString(String siteId);

    public void setCoursePropertyString(String siteId, String value);

    public String getUserPropertyString(String userId);

    public void setUserPropertyString(String userId, String value);

    public List<Activity> getPublishedActivities(String siteId, Module module);

    public List<Module> getPublishedModules(String siteId, Course course);

    public Long[] getAllPublishedActivities(String siteId);

    public boolean isUserBLocked(String siteId, String userId);

    public void blockUser(String siteId, String userId);

    public void cleanExpireDate(String siteId, String userId);

    public void unblockUser(String siteId, String userId);

}
