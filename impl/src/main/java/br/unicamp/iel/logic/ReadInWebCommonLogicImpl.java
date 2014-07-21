package br.unicamp.iel.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Logger;
import org.sakaiproject.site.api.Site;

import br.unicamp.iel.dao.ReadInWebDao;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.model.Strategy;

/**
 * Implementation of {@link ReadInWebCommonLogic}
 *
 * @author Mike Jennings (mike_jennings@unc.edu)
 *
 */
public class ReadInWebCommonLogicImpl implements ReadInWebCommonLogic {

    private static final Logger log = Logger.getLogger(ReadInWebCommonLogicImpl.class);

    @Getter
    @Setter
    private ReadInWebDao dao;


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Course> getItems() {

        List<Course> items = dao.findAll(Course.class);
        List<Site> siteList = dao.getReadInWebSites();

        return items;

    }

    /**
     * init - perform any actions required here for when this bean starts up
     */
    public void init() {
        log.info("init");
    }

    @Override
    public boolean addFunctionalWord(Course c) {
        FunctionalWord fw = new FunctionalWord();
        fw.setWord("Success");
        fw.setMeaning("Sucesso");

        fw.setCourse(dao.findById(Course.class, c.getId()));
        System.out.println(fw.getCourse().getTitle());
        dao.create(fw);
        return false;
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
