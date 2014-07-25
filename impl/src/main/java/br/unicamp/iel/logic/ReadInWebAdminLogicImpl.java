/**
 * 
 */
package br.unicamp.iel.logic;

import java.util.List;

import org.apache.log4j.Logger;

import lombok.Setter;
import br.unicamp.iel.dao.ReadInWebDao;
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
        log.info("init");
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
    public boolean updateQuestion(Question question) {
        try {
            dao.update(question);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }

    @Override
    public boolean updateActivity(Activity activity) {
        try {
            dao.update(activity);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }
}
