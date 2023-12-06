package com.example.imsbackend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (StudentInfo)表实体类
 *
 * @author makejava
 * @since 2023-12-06 20:58:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("student_info")
public class StudentInfo  {
    @TableId
    private Integer id;

    private String netId;
    private Double totalCredit;
    private Integer earnedCredit;
    private Double gradePoints;
    private String affiliatedSchool;
}
