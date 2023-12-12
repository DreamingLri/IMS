package com.example.imsbackend.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentScoreVo {
    private int id;
    private String name;
    private String netId;
    private String code;

    private int studyScore;
    private int examScore;
    private double totalScore;
    private int scoreFunction;
}
