package br.unicamp.iel.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import org.sakaiproject.genericdao.api.search.Search;

import br.unicamp.iel.dao.ReadInWebDao;

public class ModuleSets {
    @Getter
    private Module module;

    public ModuleSets(Module module) {
        this.module = module;
    }

    public Set<Activity> getActivities(ReadInWebDao dao) {
        Search search = new Search("module.id", module.getId());
        return new HashSet<Activity>(dao.findBySearch(Activity.class, search));
    }
    
    
}
