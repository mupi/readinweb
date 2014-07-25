package br.unicamp.iel.model;

import java.util.List;

import lombok.Getter;

import org.sakaiproject.genericdao.api.search.Order;
import org.sakaiproject.genericdao.api.search.Search;

import br.unicamp.iel.dao.ReadInWebDao;

public class ActivitySets {
    @Getter
    private Activity activity;

    public ActivitySets(Activity activity) {
        this.activity = activity;
    }

    public List<Strategy> getStrategies(ReadInWebDao dao) {
        Search search = new Search("activity.id", activity.getId());
        search.addOrder(new Order("position"));
        return dao.findBySearch(Strategy.class, search);
    }

    public List<Exercise> getExercises(ReadInWebDao dao) {
        Search search = new Search("activity.id", activity.getId());
        search.addOrder(new Order("position"));
        return dao.findBySearch(Exercise.class, search);
    }

    public List<Question> getQuestions(ReadInWebDao dao) {
        Search search = new Search("activity.id", activity.getId());
        search.addOrder(new Order("position"));
        return dao.findBySearch(Question.class, search);
    }

    public List<DictionaryWord> getDictionary(ReadInWebDao dao) {
        Search search = new Search("activity.id", activity.getId());
        search.addOrder(new Order("word"));
        return dao.findBySearch(DictionaryWord.class, search);
    }
}
