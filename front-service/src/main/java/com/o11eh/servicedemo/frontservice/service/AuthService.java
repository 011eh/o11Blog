package com.o11eh.servicedemo.frontservice.service;

import cn.dev33.satoken.stp.StpUtil;
import com.o11eh.servicedemo.frontservice.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private MemberService memberService;

    public void register(String email, String password) {
        memberService.register(email, password);

        //todo 发送邮件
    }

    public String login(String email, String password) {
        Member member = memberService.login(email, password);
        StpUtil.login(member.getId());
        return StpUtil.getTokenValue();
    }
}
