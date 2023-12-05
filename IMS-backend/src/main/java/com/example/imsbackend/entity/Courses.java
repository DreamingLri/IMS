package com.example.imsbackend.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("courses")
public class Courses{

    private Integer id;
    private String name;
    private Date startTime;
    private Date endTime;
    private Integer studentNumber;
    private Double credit;
    private String openedBy;

    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;
    private String delFlag;

}

