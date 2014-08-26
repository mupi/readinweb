package br.unicamp.iel.tool;

import lombok.Data;
import lombok.Setter;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Strategy;
import br.unicamp.iel.model.types.StrategyType;
import br.unicamp.iel.tool.commons.CourseComponents;

@Data
public class AdminActivityBean {
    private Integer position;
    private String image;
    private String title;
    private String text;
    private String preReading;
    private Integer etaRead;

    @Setter
    private ReadInWebAdminLogic logic;

    public boolean updateActivity(Long id){
        Activity activity = logic.getActivity(id);

        if(position != null){
            activity.setPosition(position);
        }
        if(title != null){
            activity.setTitle(title);
        }
        if(text != null){
            activity.setText(text);
        }
        if(preReading != null){
            activity.setPrereading(preReading);
        }
        if(etaRead != null){
            activity.setEtaRead(etaRead);
        }

        return logic.updateActivity(activity);
    }

    public boolean activityDataSent() {
        return position != null || image != null || title != null
                || text != null || preReading != null || etaRead != null;
    }

    /**
     * Functional word handling
     */

    private Long functionalWordId;
    private Long functionalWordCourse;
    private String functionalWord;
    private String functionalWordMeaning;

    public String updateFunctionalWord(){
        if(functionalDataSent()){
            FunctionalWord word = logic.getFunctionalWord(strategyId);
            if(functionalWord != null){
                word.setWord(functionalWord);
            }
            if(functionalWordMeaning != null){
                word.setMeaning(functionalWordMeaning);
            }

            try{
                logic.saveFunctionalWord(word);
                return CourseComponents.UPDATED;
            } catch (Exception e){
                e.printStackTrace();
                return CourseComponents.UPDATE_FAIL;
            }
        } else {
            return CourseComponents.DATA_EMPTY;
        }
    }

    public String addFunctionalWord(){
        FunctionalWord word =
                new FunctionalWord(logic.getCourse(functionalWordCourse));
        if(functionalWord != null){
            word.setWord(functionalWord);
        }
        if(functionalWordMeaning != null){
            word.setMeaning(functionalWordMeaning);
        }
        try{
            logic.saveFunctionalWord(word);
            return CourseComponents.SAVED;
        } catch (Exception e){
            e.printStackTrace();
            return CourseComponents.SAVE_FAIL;
        }
    }

    public String deleteFunctionalWord(){
        FunctionalWord word = logic.getFunctionalWord(functionalWordId);
        try {
            logic.deleteEntity(word);
        } catch (Exception e){
            e.printStackTrace();
            return CourseComponents.DELETE_FAIL;
        }
        return CourseComponents.DELETED;
    }

    public boolean functionalDataSent(){
        return functionalWord != null || functionalWordMeaning != null;
    }

    /**
     * Dictionary handling
     */

    private Long dictionaryWordId;
    private Long dictionaryWordActivity;
    private String dictionaryWord;
    private String dictionaryWordMeaning;

    public String updateDictionaryWord(){
        if(dictionaryDataSent()){
            DictionaryWord word = logic.getDictionaryWord(dictionaryWordActivity);
            if(dictionaryWord != null){
                word.setWord(dictionaryWord);
            }
            if(dictionaryWordMeaning != null){
                word.setMeaning(dictionaryWordMeaning);
            }

            try{
                logic.saveDictionaryWord(word);
                return CourseComponents.UPDATED;
            } catch (Exception e){
                e.printStackTrace();
                return CourseComponents.UPDATE_FAIL;
            }
        } else {
            return CourseComponents.DATA_EMPTY;
        }
    }

    public String addDictionaryWord(){
        DictionaryWord word =
                new DictionaryWord(logic.getActivity(dictionaryWordActivity));
        if(dictionaryWord != null){
            word.setWord(dictionaryWord);
        }
        if(dictionaryWordMeaning != null){
            word.setMeaning(dictionaryWordMeaning);
        }
        try{
            logic.saveDictionaryWord(word);
            return CourseComponents.SAVED;
        } catch (Exception e){
            e.printStackTrace();
            return CourseComponents.SAVE_FAIL;
        }
    }

    public String deleteDictionaryWord(){
        DictionaryWord word = logic.getDictionaryWord(dictionaryWordId);
        try {
            logic.deleteEntity(word);
        } catch (Exception e){
            e.printStackTrace();
            return CourseComponents.DELETE_FAIL;
        }
        return CourseComponents.DELETED;
    }

    public boolean dictionaryDataSent(){
        return dictionaryWord != null || dictionaryWordMeaning != null;
    }


    /**
     * Strategy data handling
     */

    private Long strategyId;
    private Integer strategyPosition;
    private Integer strategyType;
    private Long strategyActivity;
    private String strategyData;

    public String updateStrategy(){
        if(strategyDataSent()){
            Strategy strategy = logic.getStrategy(strategyId);
            if(strategyPosition != null){
                strategy.setPosition(strategyPosition);
            }
            if(strategyType != null){
                strategy.setType(strategyType);
            }
            if(strategyData != null){
                strategy.setBody(strategyData);
            }
            try{
                logic.saveStrategy(strategy);
                return CourseComponents.UPDATED;
            } catch (Exception e){
                e.printStackTrace();
                return CourseComponents.UPDATE_FAIL;
            }

        } else {
            return CourseComponents.DATA_EMPTY;
        }

    }

    public String saveStrategy(){
        Strategy strategy = new Strategy(logic.getActivity(strategyActivity));
        if(strategyPosition != null){
            strategy.setPosition(strategyPosition);
        }
        if(strategyType != null){
            strategy.setType(strategyType);
        }
        if(strategyData != null){
            strategy.setBody(strategyData);
        }
        try{
            logic.saveStrategy(strategy);
            return CourseComponents.SAVED;
        } catch (Exception e){
            e.printStackTrace();
            return CourseComponents.SAVE_FAIL;
        }
    }

    public String deleteStrategy(){
        Strategy strategy = logic.getStrategy(strategyId);
        try {
            logic.deleteEntity(strategy);
        } catch (Exception e){
            e.printStackTrace();
            return CourseComponents.DELETE_FAIL;
        }
        return CourseComponents.DELETED;
    }

    public boolean strategyDataSent(){
        return strategyPosition != null || strategyType != null
                || strategyData != null;
    }

    /**
     * Exercise data handling
     */

    private Long exerciseId;
    private Integer exercisePosition;
    private Long exerciseActivity;
    private String exerciseTitle;
    private String exerciseDescription;
    private String exerciseAnswer;

    public String updateExercise(){
        if(exerciseDataSent()){
            Exercise exercise = logic.getExercise(exerciseId);
            if(exercisePosition != null){
                exercise.setPosition(exercisePosition);
            }
            if(exerciseTitle != null){
                exercise.setTitle(exerciseTitle);
            }
            if(exerciseDescription != null){
                exercise.setDescription(exerciseDescription);
            }
            if(exerciseAnswer != null){
                exercise.setAnswer(exerciseAnswer);
            }

            try{
                logic.saveExercise(exercise);
                return CourseComponents.UPDATED;
            } catch (Exception e){
                e.printStackTrace();
                return CourseComponents.UPDATE_FAIL;
            }

        }
        return CourseComponents.DATA_EMPTY;
    }

    public String saveExercise(){
        Exercise exercise = new Exercise(logic.getActivity(exerciseActivity));
        if(exercisePosition != null){
            exercise.setPosition(exercisePosition);
        }
        if(exerciseTitle != null){
            exercise.setTitle(exerciseTitle);
        }
        if(exerciseDescription != null){
            exercise.setDescription(exerciseDescription);
        }
        if(exerciseAnswer != null){
            exercise.setAnswer(exerciseAnswer);
        }
        try{
            logic.saveExercise(exercise);
            return CourseComponents.SAVED;
        } catch (Exception e){
            e.printStackTrace();
            return CourseComponents.SAVE_FAIL;
        }
    }

    public String deleteExercise(){
        Exercise exercise = logic.getExercise(exerciseId);
        try {
            logic.deleteEntity(exercise);
        } catch (Exception e){
            e.printStackTrace();
            return CourseComponents.DELETE_FAIL;
        }
        return CourseComponents.DELETED;
    }

    public boolean exerciseDataSent(){
        return exercisePosition != null || exerciseTitle != null
                || exerciseDescription != null || exerciseAnswer != null;
    }

    /**
     * Grammar content handling
     */

    private Long grammarModule;
    private String grammarData;

    public String updateGrammar(){
        if(grammarDataSent()){
            Module module = logic.getModule(grammarModule);
            if(grammarData != null){
                module.setGrammar(grammarData);
            }
            try{
                logic.saveModule(module);
                return CourseComponents.SAVED;
            } catch (Exception e){
                e.printStackTrace();
                return CourseComponents.SAVE_FAIL;
            }
        }
        return CourseComponents.DATA_EMPTY;
    }

    public boolean grammarDataSent(){
        return grammarData != null;
    }

}
