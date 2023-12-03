package com.example.imsbackend.entity;

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

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String delFlag;

}
