package com.o11eh.servicedemo.front.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.o11eh.servicedemo.front.entity.Member;
import com.o11eh.servicedemo.front.projection.Projection;
import com.o11eh.servicedemo.front.repository.MemberRepository;
import com.o11eh.servicedemo.servicebase.config.BusinessException;
import com.o11eh.servicedemo.servicebase.config.RedisConfig;
import com.o11eh.servicedemo.servicebase.constants.RabbitConstants;
import com.o11eh.servicedemo.servicebase.enums.Status;
import com.o11eh.servicedemo.servicebase.utils.HttpUtil;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class MemberService {

    private RedisTemplate<String, Object> redis;
    private MemberRepository memberRepository;
    private RabbitTemplate rabbit;

    public void register(String email, String password) {
        Projection account = memberRepository.existsValidAccount(email);
        if (account != null) {
            throw BusinessException.e("账号已存在");
        }

        String token = IdUtil.simpleUUID();
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(SaSecureUtil.md5BySalt(password, email));
        redis.opsForValue().set(RedisConfig.UNACTIVATED_USER + token, member, 1, TimeUnit.HOURS);
        rabbit.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.EMAIL_ROUTING_KEY, token);
    }

    public String login(String email, String password) {
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

        member.login(HttpUtil.getIPAddress());
        memberRepository.save(member);
        StpUtil.login(member.getId());
        return StpUtil.getTokenValue();
    }

    public void activate(String token) {
        Member member = (Member) redis.opsForValue().get(RedisConfig.UNACTIVATED_USER + token);
        if (member == null) {
            throw BusinessException.e("无效验证码");
        }
        redis.delete(RedisConfig.UNACTIVATED_USER + token);
        member.setStatus(Status.Enable);
        memberRepository.save(member);
    }
}
