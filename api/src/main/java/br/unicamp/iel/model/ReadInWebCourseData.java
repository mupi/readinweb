package br.unicamp.iel.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReadInWebCourseData {
    private Integer countClasses;
    private Integer countClassesFinished;
    private Integer countUsers;
    private Integer countGraduates;

    public ReadInWebCourseData(){}

    public void sumCountGraduates(boolean userBLocked) {
        countGraduates += userBLocked ? 1 : 0;
    }
}

