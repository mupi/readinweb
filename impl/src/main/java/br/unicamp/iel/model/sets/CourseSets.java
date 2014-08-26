package br.unicamp.iel.model.sets;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.sakaiproject.genericdao.api.search.Order;
import org.sakaiproject.genericdao.api.search.Restriction;
import org.sakaiproject.genericdao.api.search.Search;

import br.unicamp.iel.dao.ReadInWebDao;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.model.Module;

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

    public List<Module> getPublishedModules(ReadInWebDao dao,
            Long[] modules){
        if(modules.length <= 0){
            return new ArrayList<Module>();
        } else {
            Search search = new Search("course.id", course.getId());
            search.addRestriction(new Restriction("id", modules));
            search.addOrder(new Order("position"));

            return dao.findBySearch(Module.class, search);
        }
    }
}
