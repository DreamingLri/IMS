package com.example.imsbackend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveVO {
    private Integer id;

    private Integer userId;
    private String name;
    private Date leaveBegin;
    private Date leaveEnd;
    private String leaveReason;
    private Integer leaveTime;
    private String leaveType;
    private String accepted;
}
