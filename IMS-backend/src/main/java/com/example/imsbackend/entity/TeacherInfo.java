package com.example.imsbackend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (TeacherInfo)表实体类
 *
 * @author makejava
 * @since 2023-12-06 20:59:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("teacher_info")
public class TeacherInfo  {
    @TableId
    private Integer id;

    private String netId;
    private Integer evaluationScore;
    private String affiliatedSchool;
    private String qualifications;
    private String researchDirection;
}
