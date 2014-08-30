package br.unicamp.iel.tool;

import lombok.Data;
import lombok.Setter;
import uk.org.ponder.rsf.state.scope.BeanDestroyer;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.tool.commons.CourseComponents;

@Data
public class FunctionalWordAjaxBean {

    @Setter
    private ReadInWebAdminLogic logic;
    @Setter
    private BeanDestroyer destroyer;

    private Long id;
    private Long course;
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
        if(functionalDataSent()){
            FunctionalWord updateword = logic.getFunctionalWord(id);
            if(word != null){
                updateword.setWord(word);
            }
            if(meaning != null){
                updateword.setMeaning(meaning);
            }

            try{
                logic.saveFunctionalWord(updateword);
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
        FunctionalWord addword =
                new FunctionalWord(logic.getCourse(course));
        if(word != null){
            addword.setWord(word);
        }
        if(meaning != null){
            addword.setMeaning(meaning);
        }
        try{
            logic.saveFunctionalWord(addword);
            return new String[] {"success", Long.toString(addword.getId()),
                    CourseComponents.SAVED };
        } catch (Exception e){
            e.printStackTrace();
            return new String[] {"failed", CourseComponents.SAVE_FAIL};
        }
    }

    public String[] delete(){
        FunctionalWord deleteword = logic.getFunctionalWord(id);
        try {
            logic.deleteEntity(deleteword);
        } catch (Exception e){
            e.printStackTrace();
            return new String[] {"failed", CourseComponents.DELETE_FAIL};
        }
        return new String[] {"success", CourseComponents.DELETED};
    }

    public boolean functionalDataSent(){
        return word != null || meaning != null;
    }

}

