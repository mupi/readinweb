package br.unicamp.iel.tool;

import java.util.Iterator;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.tool.commons.CourseComponents;
import lombok.Setter;

public class AdminBean {
    @Setter
    private String title;
    @Setter
    private String language;
    @Setter
    private String description;

    @Setter
    private Map<String, CommonsMultipartFile> files;


    @Setter
    private ReadInWebAdminLogic logic;

    public String createCourse(){
        if(title == null || language == null) {
            return CourseComponents.CREATE_FAIL;
        } else {
            try {
                Course course = new Course(title, language);
                if(description != null){
                    course.setDescription(description);
                }
                System.out.println("Salvando novo curso");
                logic.saveCourse(course);
                System.out.println("Pronto!");
            } catch (Exception e){
                e.printStackTrace();
                return CourseComponents.CREATE_FAIL;
            }
        }

        return CourseComponents.CREATED;
    }

    public String handleContentSent(){
        // Get content Type and verify if the content is valid
        // Verify if the content is valid accordling with the type passed
        // like activity, exercise or stuff
        // if all ok, insert data on the database taking care with do not insert
        // repeated data, of course.
        // If it is exercise contents, save it on the correct place considering
        // The content should come with the correct structure.
        if(files.isEmpty())
            return CourseComponents.DATA_EMPTY;

        Iterator<String> it = files.keySet().iterator();
        if(it.hasNext()){
            String filename = it.next();
            System.out.println(filename);
            CommonsMultipartFile file = files.get(filename);
            System.out.println(file.getName());
            System.out.println(file.getContentType());
            System.out.println(file.getSize());
            System.out.println(file.getStorageDescription());
        }
        return CourseComponents.DATA_SAVING_FAIL;
    }

}

