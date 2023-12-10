package com.example.imsbackend.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVO {
    private Integer id;
    private String name;
    private String teacher;
    private Date startTime;
    private Date endTime;
    private Object studentNumber;
    private Double credit;
    private Double period;
    private String openedBy;
    private Boolean selected;
}
