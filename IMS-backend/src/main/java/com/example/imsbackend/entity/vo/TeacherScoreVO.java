package com.example.imsbackend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherScoreVO {
    private int id;
    private String teacherName;
    private String courseName;

    private int evaluationScore;
    private String evaluationSuggestion;
}
