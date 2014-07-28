/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unicamp.iel.logic;

import java.util.List;

import org.sakaiproject.user.api.User;

import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Answer;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.model.Justification;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.model.ReadInWebControl;
import br.unicamp.iel.model.Strategy;

/**
 *
 * @author vsantos
 */
public interface ReadInWebCourseLogic {

    public String getUserId();

    public Course getCourse(Long course);

    public Module getModule(Long module);

    public Activity getActivity(Long activity);

    public Question getQuestion(Long question);

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

    public void saveCourse(Course course);

    public void saveModule(Module module);

    public void saveActivity(Activity activity);

    public void saveDictionaryWord(DictionaryWord dw);

    public void saveExercise(Exercise exercise);

    public void saveQuestion(Question question);

    public void saveFunctionalWord(FunctionalWord fw);

    public void saveStrategy(Strategy strategy);

    /**
     *
     * Bellow specific course methods
     *
     */

    public Answer getAnswer(Long answer);

    public boolean updateAnswer(Answer answer);

    public void saveAnswer(Answer anwser);

    public Answer getAnswerByQuestionAndUser(Long question);

    public void registerTextRead(Long activity);

    public boolean hasUserAnsweredQuestions(Long activity);

    public void registerQuestionsDone(Long activity);

    public void registerExercisesAccess(Long activity);

    public Long[] getModulesIds(Long course);

    public Long[] getQuestionsIds(Long course);

    public List<Module> getPusblishedModules(Course course);

    public List<Activity> getPusblishedActivities(Module course);

    /**
     * Old methods
     */

    public int[][] makeAccessMatrix(List<Module> lst_modulos,
            List<Activity> lst_atividades, String userId, String currentSiteId);

    public boolean isUserAdmin();

    public boolean isUserTeacher(String userId);

    public boolean checkBlockStudent(int[][] mat_actsDone,
            List<Module> lst_modulos, List<Activity> lst_atividades);

    public Justification lastJustificationData();

    public boolean sentJustification();

    public long allowsLateUserToAccess(java.util.Date date_evaluated, byte state);

    public void sendJustification(String userId, String str_justification);

    public String getCurrentPlacement();

    public boolean checkTextByControlSum(int int_controlSum);

    public boolean checkQuestionsByControlSum(int int_controlSum);

    public boolean checkExerciseByControlSum(int int_controlSum);

    public boolean checkSiteAccess(String currentSiteId, Long activity);

    public void registerAccess(String action, String viewID, Activity activity);

    public int getControlNum(String userId, Long long_currentModule,
            Long long_currentActivity);

    public int getControlNum(String userId, int int_currentModule,
            int int_currentActivity);

    public boolean checkActCompletenessByControlSum(int i);

    public User getCurrentUser();

    public boolean blockUser(Long course);

    public Long getCourseId();

    public Integer getActivityControlSum(Long id);

}
