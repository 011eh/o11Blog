package com.o11eh.servicedemo.front.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.o11eh.servicedemo.front.entity.Member;
import com.o11eh.servicedemo.front.repository.MemberRepository;
import com.o11eh.servicedemo.servicebase.config.BusinessException;
import com.o11eh.servicedemo.servicebase.config.RedisConfig;
import com.o11eh.servicedemo.servicebase.constants.RabbitConstants;
import com.o11eh.servicedemo.servicebase.enums.Status;
import com.o11eh.servicedemo.servicebase.utils.HttpUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.criteria.Predicate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class MemberService extends BaseService {

    private final RedisTemplate<String, Object> redis;
    private final MemberRepository memberRepository;
    private final RabbitTemplate rabbit;

    public MemberService(TransactionTemplate transactionTemplate,
                         RedisTemplate<String, Object> redis,
                         MemberRepository memberRepository,
                         RabbitTemplate rabbit) {
        super(transactionTemplate);
        this.redis = redis;
        this.memberRepository = memberRepository;
        this.rabbit = rabbit;
    }

    public void register(String email, String password) {
        Optional<Member> memberActivated = memberRepository.findOne((root, query, criteriaBuilder) -> {
            Predicate eqEmail = criteriaBuilder.equal(root.get("email"), email);
            Predicate neStatus = criteriaBuilder.notEqual(root.get("status"), Status.FROZEN);
            return criteriaBuilder.and(eqEmail, neStatus);
        });
        if (memberActivated.isPresent()) {
            throw BusinessException.e("账号已存在");
        }

        String token = IdUtil.simpleUUID();
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(SaSecureUtil.md5BySalt(password, email));
        memberRepository.save(member);
        redis.opsForValue().set(RedisConfig.UNACTIVATED_USER + token, member, 1, TimeUnit.HOURS);

        Map<String, String> map = new HashMap<>();
        map.put("receiver", email);
        map.put("token", token);
        rabbit.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.EMAIL_ROUTING_KEY, map);
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
        Status status = member.getStatus();
        if (status.equals(Status.FROZEN)) {
            throw BusinessException.e("账户未激活");
        }
        if (status.equals(Status.Disable)) {
            throw BusinessException.e("账户已被禁用");
        }

        member.login(HttpUtil.getIPAddress());
        memberRepository.save(member);
        StpUtil.login(member.getId());
        return StpUtil.getTokenValue();
    }

    public void activate(String token) {
        Member memberInfo = (Member) redis.opsForValue().get(RedisConfig.UNACTIVATED_USER + token);
        if (memberInfo == null) {
            throw BusinessException.e("无效验证码");
        }

        transactionTemplate.executeWithoutResult(status -> {
            Member member = memberRepository.getById(memberInfo.getId());
            member.setStatus(Status.Enable);
            memberRepository.save(member);
            memberRepository.deleteByEmailAndStatus(memberInfo.getEmail(), Status.FROZEN);
        });
        redis.delete(RedisConfig.UNACTIVATED_USER + token);
    }
}
