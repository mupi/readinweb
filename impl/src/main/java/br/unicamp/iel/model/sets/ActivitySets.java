package br.unicamp.iel.model.sets;

import java.util.List;

import lombok.Getter;

import org.sakaiproject.genericdao.api.search.Order;
import org.sakaiproject.genericdao.api.search.Restriction;
import org.sakaiproject.genericdao.api.search.Search;

import br.unicamp.iel.dao.ReadInWebDao;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.model.ReadInWebAccess;
import br.unicamp.iel.model.ReadInWebAnswer;
import br.unicamp.iel.model.ReadInWebControl;
import br.unicamp.iel.model.Strategy;

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

    public Long countQuestions(ReadInWebDao dao){
        Restriction[] r = new Restriction[]{
                new Restriction("activity.id", activity.getId()),
        };
        return dao.countBySearch(Question.class, new Search(r));
    }

    public List<DictionaryWord> getDictionary(ReadInWebDao dao) {
        Search search = new Search("activity.id", activity.getId());
        search.addOrder(new Order("word"));
        return dao.findBySearch(DictionaryWord.class, search);
    }

    public List<ReadInWebAccess> getAccesses(ReadInWebDao dao){
        Restriction[] r = new Restriction[]{
                new Restriction("activity.id", activity.getId()),
        };
        return dao.findBySearch(ReadInWebAccess.class, new Search(r));
    }

    public ReadInWebControl getControl(ReadInWebDao dao){
        Restriction[] r = new Restriction[]{
                new Restriction("activity.id", activity.getId()),
        };
        return dao.findOneBySearch(ReadInWebControl.class, new Search(r));
    }

    public List<ReadInWebAnswer> getUserAnswers(ReadInWebDao dao){
        Restriction[] r = new Restriction[]{
                new Restriction("activity.id", activity.getId()),
        };
        return dao.findBySearch(ReadInWebAnswer.class, new Search(r));
    }

    public List<ReadInWebAccess> getAccesses(ReadInWebDao dao, String user){
        Restriction[] r = new Restriction[]{
                new Restriction("activity.id", activity.getId()),
                new Restriction("user", user)
        };
        return dao.findBySearch(ReadInWebAccess.class, new Search(r));
    }

    public ReadInWebControl getControl(ReadInWebDao dao, String user){
        Restriction[] r = new Restriction[]{
                new Restriction("activity.id", activity.getId()),
                new Restriction("user", user)
        };
        return dao.findOneBySearch(ReadInWebControl.class, new Search(r));
    }

    public List<ReadInWebAnswer> getUserAnswers(ReadInWebDao dao, String user){
        Restriction[] r = new Restriction[]{
                new Restriction("activity.id", activity.getId()),
                new Restriction("user", user)
        };
        return dao.findBySearch(ReadInWebAnswer.class, new Search(r));
    }


}
