package com.o11eh.servicedemo.front.repository;

import com.o11eh.servicedemo.front.entity.Member;
import com.o11eh.servicedemo.servicebase.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, String>, JpaSpecificationExecutor<Member> {

    void deleteByEmailAndStatus(String email, Status status);
}
