package com.example.imsbackend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (CourseTime)表实体类
 *
 * @author makejava
 * @since 2023-12-09 17:56:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("course_time")
public class CourseTime  {
    @TableId
    private Integer id;

    private Integer courseId;
    private String weekday;
    private String session;
}
