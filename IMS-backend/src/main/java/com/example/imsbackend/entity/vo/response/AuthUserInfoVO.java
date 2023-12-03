package com.example.imsbackend.entity.vo.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserInfoVO {
    private int id;
    private String username;
}
