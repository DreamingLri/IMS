package com.example.imsbackend.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Exams)表实体类
 *
 * @author makejava
 * @since 2023-12-07 15:49:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("exams")
public class Exams  {
    @TableId
    private Integer id;

    private Integer name;
    private Date date;
    private String examPlace;
    private Double examDuration;
    private String invigilator;
    private Integer courseId;
    private String createBy;

    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private String delFlag;
}
