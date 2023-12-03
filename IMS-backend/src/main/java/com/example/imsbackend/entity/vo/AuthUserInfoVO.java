package com.example.imsbackend.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserInfoVO {
    private int id;
    private String username;
    private String password;
    private String address;
    private String code;
    private String identificationCode;
    private Date birthday;
    private String gender;
    private Date entryTime;
    private Date finishTime;
}
