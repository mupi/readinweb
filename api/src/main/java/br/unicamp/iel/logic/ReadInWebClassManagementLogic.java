/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unicamp.iel.logic;

import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Explanation;
import br.unicamp.iel.model.ReadInWebClass;
import br.unicamp.iel.model.ReadInWebClassData;
import br.unicamp.iel.model.ReadInWebUser;
import java.util.List;

/**
 *
 * @author vsantos
 */

public interface ReadInWebClassManagementLogic {

    public boolean createClass(Course c);

    public boolean updateClass(ReadInWebClass riwc);

    public boolean closeClass(ReadInWebClass riwc);

    public ReadInWebClassData getClassData(ReadInWebClass riwc);

    public List<ReadInWebUser> getClassUsers(ReadInWebClass riwc);

    public boolean setReadInWebUserStatus(boolean status);

    public List<Explanation> getExplanationList(ReadInWebClass riwc);

}
