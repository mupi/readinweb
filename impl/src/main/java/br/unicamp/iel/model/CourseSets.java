package br.unicamp.iel.model;

import java.util.List;

import lombok.Getter;

import org.sakaiproject.genericdao.api.search.Order;
import org.sakaiproject.genericdao.api.search.Search;

import br.unicamp.iel.dao.ReadInWebDao;

public class CourseSets {
    @Getter
    private Course course;
        
    public CourseSets(Course course) {
        this.course = course;
    }

    public List<Module> getModules(ReadInWebDao dao) {
        Search search = new Search("course.id", course.getId());
        search.addOrder(new Order("position"));
        return dao.findBySearch(Module.class, search);
    }

    public List<FunctionalWord> getFunctionalWords(ReadInWebDao dao) {    
        Search search = new Search("course.id", course.getId());
        search.addOrder(new Order("word"));
        return dao.findBySearch(FunctionalWord.class, search);
    }
}
