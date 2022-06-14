package com.o11eh.servicedemo.frontservice.service;

import com.o11eh.servicedemo.frontservice.entity.Member;
import com.o11eh.servicedemo.frontservice.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;

    public void register(Member member) {
        memberRepository.save(member);
    }
}
