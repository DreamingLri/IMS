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
    private Integer examScore;
    private Integer studyScore;
    private Double totalScore;
    private Integer scoreFunction;
    private Integer evaluationScore;
}
