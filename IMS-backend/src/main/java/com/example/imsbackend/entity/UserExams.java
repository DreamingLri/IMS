package com.example.imsbackend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (UserExams)表实体类
 *
 * @author makejava
 * @since 2023-12-07 15:49:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_exams")
public class UserExams  {

    private Integer userId;
    private Integer examId;

    private String delFlag;
}
