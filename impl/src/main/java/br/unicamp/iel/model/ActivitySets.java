package br.unicamp.iel.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import org.sakaiproject.genericdao.api.search.Search;

import br.unicamp.iel.dao.ReadInWebDao;

public class ActivitySets {
    @Getter
    private Activity activity;

    public ActivitySets(Activity activity) {
        this.activity = activity;
    }

    public Set<Strategy> getStrategies(ReadInWebDao dao) {
        Search search = new Search("activity.id", activity.getId());
        return new HashSet<Strategy>(
                dao.findBySearch(Strategy.class, search));
    }

    public Set<Exercise> getExercises(ReadInWebDao dao) {
        Search search = new Search("activity.id", activity.getId());
        return new HashSet<Exercise>(
                dao.findBySearch(Exercise.class, search));
    }

    public Set<Question> getQuestions(ReadInWebDao dao) {
        Search search = new Search("activity.id", activity.getId());
        return new HashSet<Question>(
                dao.findBySearch(Question.class, search));
    }

    public Set<DictionaryWord> getDictionary(ReadInWebDao dao) {
        Search search = new Search("activity.id", activity.getId());
        return new HashSet<DictionaryWord>(
                dao.findBySearch(DictionaryWord.class, search));
    }
}
