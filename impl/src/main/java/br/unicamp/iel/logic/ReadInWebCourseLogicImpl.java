package br.unicamp.iel.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Setter;

import org.apache.log4j.Logger;
import org.sakaiproject.genericdao.api.search.Restriction;
import org.sakaiproject.genericdao.api.search.Search;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.user.api.User;

import br.unicamp.iel.dao.ReadInWebDao;
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
import br.unicamp.iel.model.ReadInWebAccess;
import br.unicamp.iel.model.ReadInWebControl;
import br.unicamp.iel.model.ReadInWebUserControl;
import br.unicamp.iel.model.Strategy;
import br.unicamp.iel.model.sets.ActivitySets;
import br.unicamp.iel.model.sets.CourseSets;
import br.unicamp.iel.model.sets.JustificationSets;
import br.unicamp.iel.model.types.BlockStateTypes;
import br.unicamp.iel.model.types.ControlTypes;

public class ReadInWebCourseLogicImpl implements ReadInWebCourseLogic {

    private static final Logger log = Logger.getLogger(ReadInWebCourseLogic.class);

    @Setter
    private SakaiProxy sakaiProxy;

    @Setter
    private ReadInWebDao dao;

    @Setter
    private ReadInWebCommonLogic common;

    /**
     * init - perform any actions required here for when this bean starts up
     */
    public void init() {
        log.info(ReadInWebCourseLogic.class + " init");
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
    public Justification getJustification(Long justification) {
        return common.getJustification(justification);
    }

    @Override
    public ReadInWebAnswer getStudentAnswer(Long question) {
        return common.getStudentAnswer(question);
    }

    @Override
    public Exercise getExercise(Long exercise) {
        return common.getExercise(exercise);
    }

    @Override
    public ReadInWebUserControl getUserControl(String userId, String siteId) {
        return common.getUserControl(userId, siteId);
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
    public List<FunctionalWord> getFunctionalWord (Course course) {
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
    public void sendJustification(Justification justification) {
        common.saveJustification(justification);
    }

    @Override
    public void sendJustificationMessage(JustificationMessage message) {
        common.saveJustificationMessage(message);
    }

    @Override
    public void deleteJustificationMessage(JustificationMessage message) {
        common.deleteEntity(message);
    }

    @Override
    public void unblockUser(ReadInWebUserControl userControl) {
        common.unblockUser(userControl);
    }

    @Override
    public void updateBlockInfo(ReadInWebUserControl userControl,
            Date evalDate) {
        common.updateBlockInfoDate(userControl, evalDate);
    }

    @Override
    public List<Justification> getUserJustifications() {
        return common.getUserJustifications(getUserId(), getCurrentSiteId());
    }

    @Override
    public List<JustificationMessage> getJustificationMessages(
            Justification justification) {
        return common.getJustificationMessages(justification);
    }

    @Override
    public List<Justification> getClassJustifications() {
        return common.getSiteJustifications(getCurrentSite());
    }

    @Override
    public void unblockUser(){
        common.unblockUser(common.getUserControl(getUserId(),
                getCurrentSiteId()));
    }

    @Override
    public void deleteEntity(Object o) {
        common.deleteEntity(o);
    }

    @Override
    public Long countMessages(Justification j) {
        JustificationSets js = new JustificationSets(j);
        return js.countMessages(dao);
    }

    /**
     * End of common methods
     */

    @Override
    public ReadInWebAnswer getAnswer(Long answer) {
        return dao.findById(ReadInWebAnswer.class, answer);
    }

    @Override
    public boolean updateAnswer(ReadInWebAnswer answer) {
        try {
            dao.save(answer);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }

    @Override
    public void saveAnswer(ReadInWebAnswer a) {
        dao.create(a);
    }

    @Override
    public ReadInWebAnswer getAnswerByQuestionAndUser(Long question) {
        Restriction[] r = new Restriction[]{
                new Restriction("question.id", question),
                new Restriction("user", this.getUserId())
        };
        return dao.findOneBySearch(ReadInWebAnswer.class, new Search(r));
    }

    private void registerControl(Long activity, ControlTypes control){
        ActivitySets as = new ActivitySets(dao.findById(Activity.class,
                activity));
        ReadInWebControl riwc = as.getControl(dao, this.getUserId());
        if(riwc == null){
            riwc = new ReadInWebControl(as.getActivity(), this.getUserId(),
                    control.getValue());
        } else if(!ControlTypes.hasDone(control, riwc.getControl())){
            riwc.setControl(riwc.getControl() + control.getValue());
        }
        dao.save(riwc);
    }

    @Override
    public void registerTextRead(Long activity) {
        registerControl(activity, ControlTypes.TEXT);
    }

    @Override
    public boolean hasUserAnsweredQuestions(Long activity) {
        ActivitySets as = new ActivitySets(dao.findById(Activity.class,
                activity));
        return as.countQuestions(dao) == common.countUserAnswers(this.getUserId(),
                getQuestionsIds(activity));
    }

    @Override
    public void registerQuestionsDone(Long activity) {
        registerControl(activity, ControlTypes.QUESTIONS);
    }

    @Override
    public void registerExercisesAccess(Long activity) {
        registerControl(activity, ControlTypes.EXERCISE);
    }

    @Override
    public void registerAccess(String action, String viewID,
            Activity activity) {
        ReadInWebAccess riwa = new ReadInWebAccess(this.getUserId(), new Date(),
                action, viewID, activity);
        dao.create(riwa);
    }

    @Override
    public Long[] getModulesIds(Long course) {
        List<Module> modules;
        CourseSets cs = new CourseSets(dao.findById(Course.class, course));
        if(cs.getCourse() != null){
            modules = new ArrayList<Module>(cs.getModules(dao));
        } else {
            modules = new ArrayList<Module>();
        }

        return common.getListIds(modules);
    }

    @Override
    public Long[] getQuestionsIds(Long activity) {
        List<Question> questions;
        ActivitySets as =
                new ActivitySets(dao.findById(Activity.class, activity));
        if(as.getActivity() != null){
            questions = new ArrayList<Question>(as.getQuestions(dao));
        } else {
            questions = new ArrayList<Question>();
        }

        return common.getListIds(questions);
    }

    @Override
    public List<Activity> getPublishedActivities(Module module) {
        return common.getPublishedActivities(sakaiProxy.getCurrentSiteId(),
                module);
    }

    @Override
    public List<Module> getPublishedModules(Course course) {
        return common.getPublishedModules(sakaiProxy.getCurrentSiteId(),
                course);
    }

    @Override
    public void blockUser(ReadInWebUserControl userControl) {
        if(!BlockStateTypes.isUserBlocked(userControl.getState())
                && !BlockStateTypes.isUserBlocked(userControl.getState())) {
            userControl.setBlockDate(new Date(System.currentTimeMillis()));
            userControl.setBlocks(userControl.getBlocks() + 1);
            userControl.setState(BlockStateTypes.BLOCKED.getValue());
        }
    }

    @Override
    public boolean isUserLate(ReadInWebUserControl userControl) {
        Long[] ids = common.getAllPublishedActivities(userControl.getSite());
        Long published = new Long(ids.length);
        Search s = new Search(
                new Restriction[] {
                        new Restriction("user", userControl.getUser()),
                        new Restriction("activity.id", ids),
                });
        Long started = new Long(dao.countBySearch(ReadInWebControl.class, s));

        // User have not even started more than five activities
        if((published - started) > 5) return true;

        s.addRestriction(new Restriction("control", ControlTypes.getSum(),
                Restriction.LESS));
        Long finished = new Long(dao.countBySearch(ReadInWebControl.class, s));

        // User have not finished
        if((published - finished) > 5) return true;

        return false;
    }

    @Override
    public boolean remissionTimeEnded(ReadInWebUserControl userControl) {
        Long remission = common.getRemissionTime(userControl.getSite());
        Long evaluated = userControl.getEvalDate().getTime();
        Long today = System.currentTimeMillis();

        return today - evaluated > remission;
    }


    @Override
    public boolean isUserBlocked() {
        return common.isUserBLocked(common.getUserControl(getUserId(),
                getCurrentSiteId()));
    }

    @Override
    public boolean hasSentExplanation() {
        return common.hasSentJustification(getUser(), getCurrentSite());
    }

    @Override
    public Integer getActivityControlSum(Long id) {
        Search s = new Search(new Restriction[]{
                new Restriction("activity.id", id),
                new Restriction("user", getUserId())
        });
        ReadInWebControl riwc = dao.findOneBySearch(ReadInWebControl.class, s);
        if(riwc != null){
            return riwc.getControl();
        } else {
            return 0;
        }
    }

    /**
     * Proxy methods
     *
     */

    @Override
    public Long getCourseId() {
        return sakaiProxy.getCourseId();
    }

    @Override
    public Site getCurrentSite() {
        return sakaiProxy.getCurrentSite();
    }

    @Override
    public String getCurrentPlacement() {
        return sakaiProxy.getCurrentPlacement().getId();
    }

    @Override
    public User getUser() {
        return sakaiProxy.getUser();
    }

    @Override
    public boolean isUserAdmin() {
        return sakaiProxy.isSuperUser();
    }

    @Override
    public boolean isUserTeacher() {
        return sakaiProxy.isUserTeacher();
    }

    @Override
    public User getSudent(String user) {
        return sakaiProxy.getUser(user);
    }

    @Override
    public void updateJustification(Justification justification) {
        dao.save(justification);
    }
}
