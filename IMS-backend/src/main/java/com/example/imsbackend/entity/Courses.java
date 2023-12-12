package com.example.imsbackend.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("courses")
public class Courses  {
    @TableId
    private Integer id;

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
