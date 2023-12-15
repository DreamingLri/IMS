package com.example.imsbackend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    private String netId;

    private String username;
    private String password;
    private String address;
    private String code;
    private String identificationCode;
    private Date birthday;
    private String gender;
    private Date entryTime;
    private Date finishTime;

    //Student Field
    private Double totalCredit; //总学分
    private Double earnedCredit; //已获得学分
    private Double gradePoints; //平均绩点
    private String affiliatedSchool; //所属学院
    //Teacher Field
    private Double evaluationScore; //评教分数
    private String qualification; //职称
    private String researchDirection; //研究方向
    private int level; //用户等级
}
