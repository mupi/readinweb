package br.unicamp.iel.model;

import java.util.List;

import lombok.Getter;

import org.sakaiproject.genericdao.api.search.Order;
import org.sakaiproject.genericdao.api.search.Restriction;
import org.sakaiproject.genericdao.api.search.Search;

import br.unicamp.iel.dao.ReadInWebDao;

public class ModuleSets {
    @Getter
    private Module module;

    public ModuleSets(Module module) {
        this.module = module;
    }

    public List<Activity> getActivities(ReadInWebDao dao) {
        Search search = new Search("module.id", module.getId());
        search.addOrder(new Order("position"));
        return dao.findBySearch(Activity.class, search);
    }

    public List<Activity> getPublishedActivies(ReadInWebDao dao,
            Long[] activities){
        Search search = new Search("module.id", module.getId());
        search.addRestriction(new Restriction("id", activities));
        search.addOrder(new Order("position"));
        return dao.findBySearch(Activity.class, search);
    }

}
