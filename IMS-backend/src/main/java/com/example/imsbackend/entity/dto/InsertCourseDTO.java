package com.example.imsbackend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertCourseDTO {
    private Integer userId;

    private String name;
    private String teacher;
    private Date startTime;
    private Date endTime;
    private Integer studentNumber;
    private Double credit;
    private Double period;
    private String place;
    private String courseAssessment;
    private String openedBy;

    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;
    private String delFlag;
}
