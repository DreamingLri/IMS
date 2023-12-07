package com.example.imsbackend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId
    private int id;
    @TableField("net_id")
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

    private Double evaluationScore; //评教分数
    private String qualification; //职称
    private String researchDirection; //研究方向

    //Teacher Field
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;

    private String delFlag;
}
