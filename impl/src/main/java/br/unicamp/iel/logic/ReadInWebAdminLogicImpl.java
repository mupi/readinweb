/**
 *
 */
package br.unicamp.iel.logic;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.sakaiproject.genericdao.api.search.Order;
import org.sakaiproject.genericdao.api.search.Search;
import org.sakaiproject.javax.Restriction;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.user.api.User;

import lombok.Setter;
import br.unicamp.iel.dao.ReadInWebDao;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.ReadInWebAccess;
import br.unicamp.iel.model.ReadInWebAnswer;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.model.ReadInWebControl;
import br.unicamp.iel.model.ReadInWebCourseData;
import br.unicamp.iel.model.Strategy;
import br.unicamp.iel.model.sets.ActivitySets;
import br.unicamp.iel.model.sets.CourseSets;
import br.unicamp.iel.model.types.StrategyType;

/**
 * @author Virgílio N Santos
 *
 */
public class ReadInWebAdminLogicImpl implements ReadInWebAdminLogic {

    private static final Logger log = Logger.getLogger(ReadInWebCourseLogic.class);

    @Setter
    private ReadInWebDao dao;

    @Setter
    private SakaiProxy sakaiProxy;

    @Setter
    private ReadInWebCommonLogic common;

    public void init() {
        log.info(ReadInWebAdminLogic.class + " init");
    }

    @Override
    public String getUserId() {
        return common.getUserId();
    }

    @Override
    public Course getCourse(Long course) {
        return common.getCourse(course);
    }

    @Override
    public Module getModule(Long module) {
        return common.getModule(module);
    }

    @Override
    public Activity getActivity(Long activity) {
        return common.getActivity(activity);
    }

    @Override
    public Question getQuestion(Long question) {
        return common.getQuestion(question);
    }

    @Override
    public Strategy getStrategy(Long strategy) {
        return common.getStrategy(strategy);
    }

    @Override
    public Exercise getExercise(Long exercise) {
        return common.getExercise(exercise);
    }

    @Override
    public ReadInWebAnswer getStudentAnswer(Long question) {
        return common.getStudentAnswer(question);
    }

    @Override
    public List<Course> getCourses() {
        return common.getCourses();
    }

    @Override
    public List<Module> getModules(Course course) {
        return common.getModules(course);
    }

    @Override
    public List<Activity> getActivities(Module module) {
        return common.getActivities(module);
    }

    @Override
    public List<FunctionalWord> getFunctionalWords (Course course) {
       return common.getFunctionalWord(course);
    }

    @Override
    public List<DictionaryWord> getDictionary(Activity activity) {
        return common.getDictionary(activity);
    }

    @Override
    public List<Strategy> getStrategies(Activity activity) {
        return common.getStrategies(activity);
    }

    public List<Question> getQuestions(Activity activity){
        return common.getQuestions(activity);
    }

    @Override
    public List<Exercise> getExercises(Activity activity) {
        return common.getExercises(activity);
    }

    @Override
    public String getCurrentSiteId() {
        return common.getCurrentSiteId();
    }

    @Override
    public void saveCourse(Course course) {
        common.saveCourse(course);
        common.addReadInWebManager(course);
    }

    @Override
    public void saveModule(Module module) {
        common.saveModule(module);
    }

    @Override
    public void saveActivity(Activity activity) {
        common.saveActivity(activity);
    }

    @Override
    public void saveDictionaryWord(DictionaryWord dw) {
        common.saveDictionaryWord(dw);
    }

    @Override
    public void saveExercise(Exercise exercise) {
        common.saveExercise(exercise);
    }

    @Override
    public void saveQuestion(Question question) {
        common.saveQuestion(question);
    }

    @Override
    public void saveFunctionalWord(FunctionalWord fw) {
        common.saveFunctionalWord(fw);
    }

    @Override
    public void saveStrategy(Strategy strategy) {
        common.saveStrategy(strategy);
    }

    @Override
    public void deleteEntity(Object entity) {
        common.deleteEntity(entity);
    }

    /**
     * End of common methods
     */

    @Override
    public FunctionalWord getFunctionalWord(Long word) {
        return dao.findById(FunctionalWord.class, word);
    }

    @Override
    public DictionaryWord getDictionaryWord(Long word) {
        return dao.findById(DictionaryWord.class, word);
    }

    @Override
    public boolean updateQuestion(Question question) {
        try {
            Activity a = getActivity(question.getActivity().getId());
            updateActivity(a);
            dao.update(question);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }

    @Override
    public boolean updateActivity(Activity activity) {
        try {
            activity.setModified(new Date());
            dao.save(activity);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }

    @Override
    public ReadInWebCourseData getReadInWebData(Course c) {
        ReadInWebCourseData riwData = new ReadInWebCourseData(
                sakaiProxy.getReadInWebSites(c.getId()).size(), 0, 0, 0);

        List<Site> sites = sakaiProxy.getReadInWebArchivedSites(c.getId());
        riwData.setCountClassesFinished(sites.size());
        for(Site s : sites){
            List<User> users = sakaiProxy.getSiteStudents(s);
            riwData.setCountUsers(riwData.getCountUsers() + users.size());
            for(User u : users){
                //FIXME Users that never entered the course could be considered
                // graduated. By the policy, it would never happen as the user
                // is weekly tested as blocked or not, but it is still soft
                riwData.sumCountGraduates(common.isUserBLocked(
                        common.getUserControl(u.getId(), s.getId())));
            }
        }
        return riwData;
    }

    @Override
    public Activity getLastActivityAdded() {
        Search search = new Search();
        search.addOrder(new Order("id", false));
        return dao.findOneBySearch(Activity.class, search);
    }

    @Override
    public Activity getLastUpdatedActivity() {
        Search search = new Search();
        search.addOrder(new Order("modified", false));
        return dao.findOneBySearch(Activity.class, search);
    }

    @Override
    public Activity getCourseFirstActivity(Long course) {
        Search search = new Search("course.id", course);
        search.addOrder(new Order("id", true));
        Module m = dao.findOneBySearch(Module.class, search);

        search = new Search("module.id", m.getId());
        search.addOrder(new Order("id", true));
        return dao.findOneBySearch(Activity.class, search);
    }

    @Override
    public void deleteActivity(Activity activity) {
        ActivitySets as = new ActivitySets(activity);
        dao.deleteSet(new HashSet<ReadInWebAccess>(as.getAccesses(dao)));
        ReadInWebControl riwc = as.getControl(dao);
        if(riwc != null){
            dao.delete(riwc);
        }
        dao.deleteSet(new HashSet<DictionaryWord>(as.getDictionary(dao)));
        dao.deleteSet(new HashSet<Exercise>(as.getExercises(dao)));
        dao.deleteSet(new HashSet<Question>(as.getQuestions(dao)));
        dao.deleteSet(new HashSet<Strategy>(as.getStrategies(dao)));
        dao.delete(activity);
    }

    @Override
    public Exercise getActivityFirstExercise(Activity activity) {
        Search search = new Search("activity.id", activity.getId());
        search.addOrder(new Order("position", true));
        return dao.findOneBySearch(Exercise.class, search);
    }

    @Override
    public Exercise getLastExerciseAdded() {
        Search search = new Search();
        search.addOrder(new Order("id", false));
        return dao.findOneBySearch(Exercise.class, search);
    }

    @Override
    public Exercise getLastUpdatedExercise() {
        Search search = new Search();
        search.addOrder(new Order("modified", false));
        return dao.findOneBySearch(Exercise.class, search);
    }

    @Override
    public Long getCourseId() {
        return sakaiProxy.getCourseId();
    }
}
