package com.example.imsbackend.entity;

import java.util.Date;

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
@TableName("leave_table")
public class Leave  {
    @TableId
    private Integer id;

    private Integer userId;
    private Date leaveBegin;
    private Date leaveEnd;
    private String leaveReason;
    private Integer leaveTime;
    private String leaveType;
    private String accepted;
}
