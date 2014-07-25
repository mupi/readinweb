package br.unicamp.iel.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Setter;

import org.apache.log4j.Logger;
import org.sakaiproject.genericdao.api.search.Restriction;
import org.sakaiproject.genericdao.api.search.Search;
import org.sakaiproject.user.api.User;

import br.unicamp.iel.dao.ReadInWebDao;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Answer;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.CourseSets;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.model.Justification;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.model.ReadInWebControl;
import br.unicamp.iel.model.Strategy;

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
    public Answer getStudentAnswer(Long question) {
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
    
    /** 
     * End of common methods
     */
    
    @Override
    public Answer getAnswer(Long answer) {
        return dao.findById(Answer.class, answer);
    }
    
    @Override
    public boolean updateAnswer(Answer answer) {
        try {
            dao.save(answer);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }

    @Override
    public void saveAnswer(Answer a) {
        dao.create(a);
    }
    
    @Override
    public Answer getAnswerByQuestionAndUser(Long question) {
        Restriction[] r = new Restriction[]{
                new Restriction("question.id", question),
                new Restriction("user", this.getUserId())
        };
        return dao.findOneBySearch(Answer.class, new Search(r));
    }
    
    @Override
    public int[][] makeAccessMatrix(List<Module> lst_modulos,
            List<Activity> lst_atividades, String userId, String currentSiteId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isUserAdmin() {
        return sakaiProxy.isSuperUser();
    }

    @Override
    public boolean isUserTeacher(String userId) {
        // TODO Auto-generated method stub
        // Verify how Sakai tests it
        return false;
    }

    @Override
    public boolean checkBlockStudent(int[][] mat_actsDone,
            List<Module> lst_modulos, List<Activity> lst_atividades) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Justification lastJustificationData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean sentJustification() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public long allowsLateUserToAccess(Date date_evaluated, byte state) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void sendJustification(String userId, String str_justification) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getCurrentPlacement() {
        return sakaiProxy.getCurrentPlacement().getId();
    }


    @Override
    public boolean checkTextByControlSum(int int_controlSum) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkQuestionsByControlSum(int int_controlSum) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkExerciseByControlSum(int int_controlSum) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkSiteAccess(String currentSiteId, Long activity) {
        // TODO Verificar se o user id tem acesso a esse site.
        return true;
    }

    @Override
    public void registerAccess(String string, String viewID,
            Activity currentActivity) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getControlNum(String userId, Long long_currentModule,
            Long long_currentActivity) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getControlNum(String userId, int int_currentModule,
            int int_currentActivity) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean checkActCompletenessByControlSum(int i) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public User getCurrentUser() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean controlExercise(User riw_currentUser,
            Activity riw_currentActivity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean controlText(User usuario, Activity curActivity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean controlQuestion(User usuario, Activity curActivity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Long[] getModulesIds(long course) {
        List<Module> modules;
        
        Course c = dao.findById(Course.class, course);
        CourseSets cs = new CourseSets(c); 
        if(c != null){
            modules = new ArrayList<Module>(cs.getModules(dao));
        } else {
            modules = new ArrayList<Module>();
        }

        return common.getListIds(modules);
    }

    @Override
    public boolean blockUser(long course) {
        System.out.println("Foram encontrados " 
                + getModulesIds(course).length 
                + "Modulos");
        Search s_a = new Search(new Restriction("module.id", 
                getModulesIds(course)));

        List<Activity> activities = dao.findBySearch(Activity.class, s_a);
        System.out.println("Foram encontradas " 
                + activities.size() 
                + "Atividades");

        Long[] ids = common.getListIds(activities);

        Search s = new Search(
                new Restriction[] {
                        new Restriction("user", this.getUserId()),
                        new Restriction("activity.id", ids),
                        new Restriction("control", 7, Restriction.LESS)
                });

        return dao.countBySearch(ReadInWebControl.class, s) > 5;
    }

    @Override
    public List<ReadInWebControl> getUserJob(Long course) {
        Long[] activities = common.getListIds(
                dao.findBySearch(Activity.class, 
                        new Search("module", 
                                getModulesIds(course))));

        Restriction[] rs = {
                new Restriction("user", this.getUserId()),
                new Restriction("activity.id", activities)
        };

        Search s = new Search(rs);

        return dao.findBySearch(ReadInWebControl.class, s);
    }

    @Override
    public Integer getActivityControlSum(long activity) {
        Search s = new Search(
                new Restriction[] {
                        new Restriction("user", this.getUserId()),
                        new Restriction("activity.id", activity),
                });
        ReadInWebControl riwc = dao.findOneBySearch(ReadInWebControl.class, s);
        if(riwc == null)
            return 0;
        else 
            return riwc.getControl();
    }

    @Override
    public Long getCourseId() {
        return sakaiProxy.getCourseId();
    }
   
}
