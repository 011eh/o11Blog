package com.o11eh.servicedemo.front.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.o11eh.servicedemo.front.entity.Member;
import com.o11eh.servicedemo.front.projection.Projection;
import com.o11eh.servicedemo.front.repository.MemberRepository;
import com.o11eh.servicedemo.servicebase.config.BusinessException;
import com.o11eh.servicedemo.servicebase.enums.Status;
import com.o11eh.servicedemo.servicebase.utils.HttpUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;

    public String register(String nickName, String email, String password) {
        Projection account = memberRepository.existsValidAccount(email);

        if (account != null) {
            throw BusinessException.e("账号已存在");
        }

        Member member = new Member();
        member.setNickName(nickName);
        member.setEmail(email);
        member.setPassword(SaSecureUtil.md5BySalt(password, email));
        memberRepository.save(member);
        return member.getId();
    }

    public Member login(String email, String password) {
        String encrypt = SaSecureUtil.md5BySalt(password, email);
        Optional<Member> result = memberRepository.findOne((root, query, criteriaBuilder) -> query.where(
                criteriaBuilder.equal(root.get("email"), email),
                criteriaBuilder.equal(root.get("password"), encrypt)).getRestriction());

        if (!result.isPresent()) {
            throw BusinessException.e("账户或密码错误");
        }
        Member member = result.get();
        Status memberStatus = member.getStatus();
        if (memberStatus.equals(Status.FROZEN)) {
            throw BusinessException.e("账户未激活");
        }
        if (memberStatus.equals(Status.Disable)) {
            throw BusinessException.e("账户已被禁用");
        }

        memberRepository.save(member.login(HttpUtil.getIPAddress()));
        return member;
    }
}
