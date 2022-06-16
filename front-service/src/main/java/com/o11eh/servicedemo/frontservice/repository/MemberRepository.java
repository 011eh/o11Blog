package com.o11eh.servicedemo.frontservice.repository;

import com.o11eh.servicedemo.frontservice.entity.Member;
import com.o11eh.servicedemo.frontservice.projection.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, String>, JpaSpecificationExecutor<Member> {
    @Query(value = "select id from front_member where (email = ?1) and status != 2 limit 1", nativeQuery = true)
    Projection existsValidAccount(String email);
}
