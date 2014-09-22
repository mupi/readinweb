/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unicamp.iel.logic;

import java.util.Date;
import java.util.List;

import org.sakaiproject.site.api.Site;
import org.sakaiproject.user.api.User;

import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.JustificationMessage;
import br.unicamp.iel.model.ReadInWebAnswer;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.model.Justification;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.model.ReadInWebUserControl;
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

    public ReadInWebAnswer getStudentAnswer(Long question);

    public ReadInWebUserControl getUserControl(String userId, String siteId);

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

    public void sendJustification(Justification justification);

    public void sendJustificationMessage(JustificationMessage message);

    public void deleteJustificationMessage(JustificationMessage message);

    public void unblockUser(ReadInWebUserControl userControl);

    public void deleteEntity(Object o);

    /**
     *
     * Bellow specific course methods
     *
     */

    public ReadInWebAnswer getAnswer(Long answer);

    public boolean updateAnswer(ReadInWebAnswer answer);

    public void saveAnswer(ReadInWebAnswer anwser);

    public ReadInWebAnswer getAnswerByQuestionAndUser(Long question);

    public void registerTextRead(Long activity);

    public boolean hasUserAnsweredQuestions(Long activity);

    public void registerQuestionsDone(Long activity);

    public void registerExercisesAccess(Long activity);

    public Long[] getModulesIds(Long course);

    public Long[] getQuestionsIds(Long course);

    public List<Module> getPublishedModules(Course course);

    public List<Activity> getPublishedActivities(Module course);

    public void blockUser(ReadInWebUserControl userControl);

    public boolean isRemissionTime(ReadInWebUserControl userControl);

    public boolean hasRemissionTimeEnded(ReadInWebUserControl userControl);

    public boolean isUserLate(ReadInWebUserControl userControl);

    public void updateBlockInfo(ReadInWebUserControl userControl, Date evalDate);

    public Long getCourseId();

    public Integer getActivityControlSum(Long id);

    public void unblockUser();

    public Site getCurrentSite();

    public boolean isUserAdmin();

    public String getCurrentPlacement();

    public void registerAccess(String action, String viewID, Activity activity);

    public User getUser();

    public boolean hasSentExplanation();

    public List<Justification> getUserJustifications();

    public List<JustificationMessage> getJustificationMessages(
            Justification justification);

    public Justification getJustification(Long justification);

    public boolean isUserTeacher();

    public List<Justification> getClassJustifications();

    public User getSudent(String user);

    public Long countMessages(Justification j);

    public Exercise getExercise(Long exercise);

    public void updateJustification(Justification justification);

    public boolean isUserBlocked();

}
