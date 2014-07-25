package br.unicamp.iel.model;

import java.util.HashSet;
import java.util.Set;

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

    public Set<Module> getModules(ReadInWebDao dao) {
        Search search = new Search("course.id", course.getId());
        search.addOrder(new Order("position"));
        return new HashSet<Module>(dao.findBySearch(Module.class, search));
    }

    public Set<FunctionalWord> getFunctionalWords(ReadInWebDao dao) {    
        Search search = new Search("course.id", course.getId());
        search.addOrder(new Order("word"));
        return new HashSet<FunctionalWord>(
                dao.findBySearch(FunctionalWord.class, search));
    }
}
