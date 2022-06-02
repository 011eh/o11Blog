package com.o11eh.servicedemo.frontservice.repository;

import com.o11eh.servicedemo.frontservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
