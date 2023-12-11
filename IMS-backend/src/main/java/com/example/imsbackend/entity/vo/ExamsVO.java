package com.example.imsbackend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamsVO {
    private Integer id;
    private String name;
    private Date date;
    private Date startTime;
    private Date endTime;
    private String examPlace;
    private Double examDuration;
    private String invigilator;
    private Integer courseId;
    private Boolean selected;
}
