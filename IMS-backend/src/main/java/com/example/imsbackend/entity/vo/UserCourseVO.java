package com.example.imsbackend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCourseVO {
    private int id;
    private String courseName;
    private String teacher;
    private String place;
    private int studentNumber;
    private int selectedNumber;
}
