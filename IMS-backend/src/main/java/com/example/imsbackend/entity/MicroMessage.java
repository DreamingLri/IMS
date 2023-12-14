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
@TableName("micro_message")
public class MicroMessage  {
    @TableId
    private Integer id;

    private Integer fromUserId;
    private String fromUserName;
    private Integer toUserId;
    private String toUserName;
    private String message;
}
