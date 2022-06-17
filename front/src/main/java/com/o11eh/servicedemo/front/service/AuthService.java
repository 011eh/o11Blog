package com.o11eh.servicedemo.front.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.IdUtil;
import com.o11eh.servicedemo.front.entity.Member;
import com.o11eh.servicedemo.servicebase.config.RedisConfig;
import com.o11eh.servicedemo.servicebase.constants.RabbitConstants;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class AuthService {

    private MemberService memberService;
    private RedisTemplate<String, Object> redisTemplate;
    private RabbitTemplate rabbitTemplate;

    public void register(String nickName, String email, String password) {
        String memberId = memberService.register(nickName, email, password);
        String token = IdUtil.simpleUUID();

        redisTemplate.opsForValue().set(RedisConfig.UNACTIVATED_USER + StrPool.COLON + token, memberId, 1, TimeUnit.HOURS);
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.EMAIL_ROUTING_KEY, token);
    }

    public String login(String email, String password) {
        Member member = memberService.login(email, password);
        StpUtil.login(member.getId());
        return StpUtil.getTokenValue();
    }
}
