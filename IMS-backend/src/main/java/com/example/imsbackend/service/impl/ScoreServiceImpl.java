package com.example.imsbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.imsbackend.entity.Score;
import com.example.imsbackend.mapper.ScoreMapper;
import com.example.imsbackend.service.ScoreService;
import org.springframework.stereotype.Service;

@Service("scoreService")
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {

}
