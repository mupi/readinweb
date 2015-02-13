/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unicamp.iel.logic;

import java.util.List;

import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.model.ReadInWebAnswer;
import br.unicamp.iel.model.ReadInWebCourseData;
import br.unicamp.iel.model.Strategy;

/**
 *
 * @author Virgilio N Santos
 */
public interface ReadInWebAdminLogic {

	public String getUserId();

	public Course getCourse(Long course);

	public Module getModule(Long module);

	public Activity getActivity(Long activity);

	public Question getQuestion(Long question);

	public Strategy getStrategy(Long strategy);

	public Exercise getExercise(Long exercise);

	public ReadInWebAnswer getStudentAnswer(Long question);

	public List<Course> getCourses();

	public List<Module> getModules(Course course);

	public List<Activity> getActivities(Module module);

	public List<FunctionalWord> getFunctionalWords(Course course);

	public List<DictionaryWord> getDictionary(Activity activity);

	public List<Strategy> getStrategies(Activity activity);

	public List<Question> getQuestions(Activity activity);

	public List<Exercise> getExercises(Activity activity);

	public String getCurrentSiteId();

	public void saveCourse(Course course);

	public void saveModule(Module module);

	public void saveActivity(Activity activity);

	public void saveDictionaryWord(DictionaryWord dw);

	public void saveExercise(Exercise exercise);

	public void saveQuestion(Question question);

	public void saveFunctionalWord(FunctionalWord fw);

	public void saveStrategy(Strategy strategy);

	public boolean updateQuestion(Question question);

	public boolean updateActivity(Activity activity);

	public ReadInWebCourseData getReadInWebData(Course c);

	public void deleteEntity(Object entity);

	public FunctionalWord getFunctionalWord(Long word);

	public DictionaryWord getDictionaryWord(Long word);

	public Activity getLastActivityAdded();

	public Activity getLastUpdatedActivity();

	public Activity getCourseFirstActivity(Long course);

	public void deleteActivity(Activity activity);

	public Long getCourseId();

	public Exercise getLastExerciseAdded();

	public Exercise getLastUpdatedExercise();

	public Exercise getActivityFirstExercise(Activity activity);

	public Module getCourseFirstModule(Long course);

	public Activity getCourseFirstByModuleActivity(Long course);

}
