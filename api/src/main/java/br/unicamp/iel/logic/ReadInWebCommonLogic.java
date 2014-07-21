/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unicamp.iel.logic;

import java.util.List;

import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.model.Strategy;

/**
 *
 * @author vsantos
 */
public interface ReadInWebCommonLogic {
    
    public List<Course> getItems();

	public boolean addFunctionalWord(Course c);

	public void loadInitialCSVData();

    public Long[] getListIds(List<?> list);
    
    public void saveCourse(Course c);

    public void saveModule(Module m);

    public void saveActivity(Activity a);

    public void saveDictionaryWord(DictionaryWord dw);

    public void saveExercise(Exercise e);

    public void saveQuestion(Question q);

    public void saveFunctionalWord(FunctionalWord fw);

    public void saveStrategy(Strategy strategy);
}
