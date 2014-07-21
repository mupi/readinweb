/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unicamp.iel.logic;

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

import java.util.Collection;
import java.util.List;

import org.sakaiproject.tool.api.Placement;
import org.sakaiproject.user.api.User;

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

    public void registerAccess(String string, String viewID,
            Activity currentActivity);

    public int getControlNum(String userId, Long long_currentModule,
            Long long_currentActivity);

    public int getControlNum(String userId, int int_currentModule,
            int int_currentActivity);

    public boolean checkActCompletenessByControlSum(int i);


    public User getCurrentUser();

    public boolean controlExercise(User riw_currentUser,
            Activity riw_currentActivity);

    public boolean controlText(User usuario, Activity curActivity);

    public void saveAnswer(Answer savingAnswer);

    public boolean controlQuestion(User usuario, Activity curActivity);

    public boolean blockUser(long course);

    public List<ReadInWebControl> getUserJob(Long course);

    public Integer getActivityControlSum(long activity);

    public Long[] getModulesIds(long course);

    public Long getCourseId();
}
