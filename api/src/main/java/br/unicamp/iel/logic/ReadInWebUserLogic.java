/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unicamp.iel.logic;

import br.unicamp.iel.model.Explanation;
import br.unicamp.iel.model.ReadInWebClass;
import br.unicamp.iel.model.ReadInWebUser;
import br.unicamp.iel.model.ReadInWebUserAction;
import java.util.List;

/**
 *
 * @author vsantos
 */
public interface ReadInWebUserLogic {
    public enum Action {
        readText,
        answeredQuestion,
        readStrategies,
        madeExercise,
    }

    public List<ReadInWebUserAction> getReadInWebUserData(ReadInWebUser riwu);

    public boolean createAction(ReadInWebClass riwc, ReadInWebUser riwu,
            Action a, Object riwObject);

    public boolean sendExplanation(ReadInWebUser riwu, Explanation e);

}
