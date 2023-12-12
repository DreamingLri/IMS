package com.example.imsbackend.entity;


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
@TableName("score")
public class Score  {
    @TableId
    private Integer id;

    private Integer userId;
    private Integer courseId;
    private Integer examScore; //考试分
    private Integer studyScore; //平时分
    private Double totalScore; //总分
    private Integer scoreFunction; //评分模式 1: 40%平时分, 60%考试分 2: 50%平时分, 50%考试分 3: 100%平时分
    private Integer evaluationScore; //评教分
}
