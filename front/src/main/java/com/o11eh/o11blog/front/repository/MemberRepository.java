package com.o11eh.o11blog.front.repository;

import com.o11eh.o11blog.servicebase.entity.front.Member;
import com.o11eh.o11blog.servicebase.enums.Status;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberRepository extends BaseRepository<Member>, JpaSpecificationExecutor<Member> {

    void deleteByEmailAndStatus(String email, Status status);
}
