package com.example.imsbackend.controller;

import com.example.imsbackend.entity.MicroMessage;
import com.example.imsbackend.service.MicroMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MicroMessageController {
    private final MicroMessageService microMessageService;
    @GetMapping("/getMessageByUserId")
    public List<MicroMessage> getMessageByUserId(Integer userId){
        return microMessageService.getMessageByUserId(userId);
    }

    @PostMapping("/sendMessage")
    public boolean sendMessage(@RequestBody MicroMessage message){
        return microMessageService.sendMessage(message);
    }
}
