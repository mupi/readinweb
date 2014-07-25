package br.unicamp.iel.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Setter;

import org.apache.log4j.Logger;
import org.sakaiproject.genericdao.api.search.Restriction;
import org.sakaiproject.genericdao.api.search.Search;

import br.unicamp.iel.dao.ReadInWebDao;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.ActivitySets;
import br.unicamp.iel.model.Answer;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.CourseSets;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.ModuleSets;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.model.Strategy;

/**
 * Implementation of {@link ReadInWebCommonLogic}
 *
 * @author Virgilio N Santos
 *
 */
public class ReadInWebCommonLogicImpl implements ReadInWebCommonLogic {

    private static final Logger log = Logger.getLogger(ReadInWebCommonLogicImpl.class);

    @Setter
    private ReadInWebDao dao;
    
    @Setter
    private SakaiProxy sakaiProxy;

    public void init() {
        log.info("init");
    }

    @Override
    public void loadInitialCSVData() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Long[] getListIds(List<?> list){
        Long[] result = new Long[list.size()];
        
        Iterator<?> it = list.iterator();
        int i = 0;
        int size = list.size();
        while(it.hasNext() && i < size){
            Object o = it.next();
            if(o instanceof Activity){
                Activity a = (Activity) o;
                result[i] = (a.getId());
            } else if(o instanceof Module){
                Module m = (Module) o;
                result[i] = (m.getId());
            }
            i++;
        }

        return result;
    }
    
    @Override
    public String getUserId() {
        return sakaiProxy.getCurrentUserId();
    }
    
    @Override
    public Course getCourse(Long course) {
        return dao.findById(Course.class, course);        
    }
    
    @Override
    public Module getModule(Long module) {
        return dao.findById(Module.class, module);
    }
    
    @Override
    public Activity getActivity(Long activity) {
        return dao.findById(Activity.class, activity);
    }
   
    @Override
    public Question getQuestion(Long question) {
        return dao.findById(Question.class, question);
    }
    
    @Override
    public Answer getStudentAnswer(Long question) {
        Answer answer = dao.findOneBySearch(Answer.class, 
                new Search(new Restriction[]{
                        new Restriction("question.id", question),
                        new Restriction("user", getUserId()),
                }));

        if(answer == null) 
            return new Answer();
        else 
            return answer;         
    }
    
    @Override
    public List<Course> getCourses() {
        return dao.findAll(Course.class);
    }
    
    @Override
    public List<Module> getModules(Course course) {
        CourseSets cs = new CourseSets(course);
        return cs.getModules(dao);
    }
    
    @Override
    public List<Activity> getActivities(Module module) {
        ModuleSets ms = new ModuleSets(module);
        return new ArrayList<Activity>(ms.getActivities(dao));
    }

    @Override
    public List<FunctionalWord> getFunctionalWord (Course course) {
        CourseSets cs = new CourseSets(course);
        return new ArrayList<FunctionalWord>(cs.getFunctionalWords(dao));
    }

    @Override
    public List<DictionaryWord> getDictionary(Activity activity) {
        ActivitySets as = new ActivitySets(activity);
        return new ArrayList<DictionaryWord>(as.getDictionary(dao));
    }

    @Override
    public List<Strategy> getStrategies(Activity activity) {
        ActivitySets as = new ActivitySets(activity);
        return new ArrayList<Strategy>(as.getStrategies(dao));
    }

    public List<Question> getQuestions(Activity activity){
        ActivitySets as = new ActivitySets(activity);
        return new ArrayList<Question>(as.getQuestions(dao));
    }
    
    @Override
    public List<Exercise> getExercises(Activity activity) {
        ActivitySets as = new ActivitySets(activity);
        return new ArrayList<Exercise>(as.getExercises(dao));
    }
            
    @Override
    public String getCurrentSiteId() {
        return sakaiProxy.getCurrentSiteId();
    }
    
    @Override
    public void saveCourse(Course c) {
        dao.create(c);        
    }

    @Override
    public void saveModule(Module m) {
        dao.create(m);        
    }

    @Override
    public void saveActivity(Activity a) {
        dao.create(a);
    }

    @Override
    public void saveDictionaryWord(DictionaryWord dw) {
        dao.create(dw);
    }

    @Override
    public void saveExercise(Exercise e) {
        dao.create(e);
    }

    @Override
    public void saveQuestion(Question q) {
        dao.create(q);        
    }

    @Override
    public void saveFunctionalWord(FunctionalWord fw) {
        dao.create(fw);
    }

    @Override
    public void saveStrategy(Strategy strategy) {
        dao.create(strategy);        
    }

}
