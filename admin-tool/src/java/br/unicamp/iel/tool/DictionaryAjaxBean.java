package br.unicamp.iel.tool;

import lombok.Data;
import lombok.Setter;
import uk.org.ponder.rsf.state.scope.BeanDestroyer;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.tool.commons.CourseComponents;

@Data
public class DictionaryAjaxBean {
    @Setter
    private ReadInWebAdminLogic logic;
    @Setter
    private BeanDestroyer destroyer;

    private Long id;
    private Long activity;
    private String word;
    private String meaning;

    private String[] update;
    public String[] getUpdate(){
        update = update();
        destroyer.destroy();
        return update;
    }

    private String[] delete;
    public String[] getDelete(){
        delete = delete();
        destroyer.destroy();
        return delete;
    }

    private String[] add;
    public String[] getAdd(){
        add = add();
        destroyer.destroy();
        return add;
    }

    public String[] update(){
        if(dictionaryDataSent()){
            DictionaryWord updateword = logic.getDictionaryWord(id);
            if(word != null){
                updateword.setWord(word);
            }
            if(meaning != null){
                updateword.setMeaning(meaning);
            }

            try{
                logic.saveDictionaryWord(updateword);
                return new String[]{"success", CourseComponents.UPDATED};
            } catch (Exception e){
                e.printStackTrace();
                return new String[]{"failed", CourseComponents.UPDATE_FAIL};
            }
        } else {
            return new String[]{"failed", CourseComponents.DATA_EMPTY};
        }
    }

    public String[] add(){
        DictionaryWord addword =
                new DictionaryWord(logic.getActivity(activity));
        if(word != null){
            addword.setWord(word);
        }
        if(meaning != null){
            addword.setMeaning(meaning);
        }
        try{
            logic.saveDictionaryWord(addword);
            return new String[] {"success", Long.toString(addword.getId()),
                    CourseComponents.SAVED};
        } catch (Exception e){
            e.printStackTrace();
            return new String[] {"failed", CourseComponents.SAVE_FAIL};
        }
    }

    public String[] delete(){
        DictionaryWord deleteword = logic.getDictionaryWord(id);
        try {
            logic.deleteEntity(deleteword);
        } catch (Exception e){
            e.printStackTrace();
            return new String[] {"failed", CourseComponents.DELETE_FAIL};
        }
        return new String[] {"success", CourseComponents.DELETED};
    }

    public boolean dictionaryDataSent(){
        return word != null || meaning != null;
    }
}

