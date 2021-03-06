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
import br.unicamp.iel.model.Justification;
import br.unicamp.iel.model.JustificationMessage;
import br.unicamp.iel.model.ReadInWebAnswer;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.model.ReadInWebUserControl;
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

    public Strategy getStrategy(Long strategy);

    public Exercise getExercise(Long exercise);

    public ReadInWebUserControl getUserControl(String userId, String siteId);

    public Long countUserAnswers(String user, Long[] ids);

    public ReadInWebAnswer getStudentAnswer(Long question);

    public List<Course> getCourses();

    public List<Module> getModules(Course course);

    public List<Activity> getActivities(Module module);

    public List<FunctionalWord> getFunctionalWord(Course course);

    public List<DictionaryWord> getDictionary(Activity activity);

    public List<Strategy> getStrategies(Activity activity);

    public List<Question> getQuestions(Activity activity);

    public List<Exercise> getExercises(Activity activity);

    public List<Justification> getUserJustifications(String user,
            String siteId);

    public List<JustificationMessage> getJustificationMessages(
            Justification justification);

    public List<Justification> getSiteJustifications(String siteId);

    public String getCurrentSiteId();

    public void saveCourse(Course c);

    public void saveModule(Module m);

    public void saveActivity(Activity a);

    public void saveDictionaryWord(DictionaryWord dw);

    public void saveExercise(Exercise e);

    public void saveQuestion(Question q);

    public void saveFunctionalWord(FunctionalWord fw);

    public void saveStrategy(Strategy strategy);

    public void saveJustification(Justification j);

    public void saveJustificationMessage(JustificationMessage jm);

    public String getDefaultCoursePropertyString(Course course);

    public String getDefaultUserPropertyString();

    public String userPropertySkelString(String siteId);

    public String getCoursePropertyString(Site site);

    public void setCoursePropertyString(String siteId, String value);

    public String getUserPropertyString(String userId);

    public void setUserPropertyString(String userId, String value);

    public List<Activity> getPublishedActivities(String siteId, Module module);

    public List<Module> getPublishedModules(String siteId, Course course);

    public Long[] getAllPublishedActivities(String siteId);

    public boolean isActiveJustification(Justification justification);

    public boolean isUserBLocked(ReadInWebUserControl userControl);

    public void blockUser(ReadInWebUserControl userControl);

    public void cleanUserStatus(ReadInWebUserControl userControl);

    public void unblockUser(ReadInWebUserControl userControl);

    public Long getUserBlockingDate(ReadInWebUserControl userControl);

    public Long getRemissionTime(String siteId);

    public boolean isModulePublished(Site riwClass, Long module);

    public boolean isActivityPublished(Site riwClass, Long module, Long activity);

    public Integer getUserBlocks(ReadInWebUserControl userControl);

    public User getTeacher(String teacherId);

    public boolean idiomCourseExists(String idiom);

    public void addReadInWebAdmin();

    public void addReadInWebManager(Course course);

    public void deleteEntity(Object entity);

    public Justification getJustification(Long justification);

    public List<Justification> getSiteJustifications(Site currentSite);

    public Long countPublishedActivities(Site site);

    public Long countUsers(Site site);

    public Date getStartDate(Site site);

    public Boolean getReadInWebClassState(Site site);

    public void setClassState(Site site, Boolean classState);

    public void updateBlockInfoDate(ReadInWebUserControl userControl, Date evalDate);

    public void releaseActivities(Site riwClass);

}


