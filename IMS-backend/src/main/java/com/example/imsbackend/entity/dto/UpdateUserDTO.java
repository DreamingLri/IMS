package com.example.imsbackend.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {

    @NotNull(message = "id不能为空")
    private int id;
    @NotBlank(message = "NetId不能为空")
    private String netId;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "地址不能为空")
    private String address;

    @NotBlank(message = "学号不能为空")
    private String code;

    @NotBlank(message = "身份证不能为空")
    @Length(max = 18, min = 18, message = "身份证必须为18位")
//    @Pattern(regexp = "^(1[1-5]|2[1-3]|3[1-7]|4[1-6]|5[0-4]|6[1-5]|71|8[1-2])\\d{4}\\d{4}(0[1-9]|1[0-2])(0[1-9]|[1-2]\\d|3[0-1])\\d{3}([0-9Xx])$",
//    message = "身份证格式错误")
    private String identificationCode;

//    @NotBlank(message = "生日不能为空")
    private Date birthday;

    @NotBlank(message = "性别不能为空")
    private String gender;

//    @NotBlank(message = "入学日期不能为空")
    private Date entryTime;

//    @NotBlank(message = "离校日期不能为空")
    private Date finishTime;
}
