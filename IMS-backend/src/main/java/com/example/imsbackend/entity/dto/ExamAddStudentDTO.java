package com.example.imsbackend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamAddStudentDTO {
    private Integer id; //exam id
    private String student; //string of student
}
