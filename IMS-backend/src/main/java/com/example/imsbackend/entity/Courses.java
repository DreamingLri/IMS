package com.example.imsbackend.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("courses")
public class Courses{
    @TableId
    private Integer id;
    private String name;
    private String teacher;
    @TableField("start_time")
    private Date startTime;
    @TableField("end_time")
    private Date endTime;
    @TableField("student_number")
    private Object studentNumber;
    private Double credit;
    private Double period;
    @TableField("opened_by")
    private String openedBy;

    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;
    private String delFlag;

}

