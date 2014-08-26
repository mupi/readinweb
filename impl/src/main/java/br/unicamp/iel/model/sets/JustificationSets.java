package br.unicamp.iel.model.sets;

import java.util.List;

import org.sakaiproject.genericdao.api.search.Order;
import org.sakaiproject.genericdao.api.search.Search;

import br.unicamp.iel.dao.ReadInWebDao;
import br.unicamp.iel.model.Justification;
import br.unicamp.iel.model.JustificationMessage;
import lombok.Getter;

public class JustificationSets {
    @Getter
    Justification justification;

    public JustificationSets(Justification justification){
        this.justification = justification;
    }

    public List<JustificationMessage> getJustificationMessages(ReadInWebDao dao) {
        Search search = new Search("justification.id", justification.getId());
        search.addOrder(new Order("dateSent"));
        return dao.findBySearch(JustificationMessage.class, search);
    }


}
