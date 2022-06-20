package com.o11eh.servicedemo.front.entity;

import com.o11eh.servicedemo.servicebase.enums.Status;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@DynamicInsert
@DynamicUpdate
@Entity(name = "front_member")
public class Member extends BaseEntry {

    @Email
    private String email;
    private String password;

    @Length(min = 2, max = 16)
    private String nickName;

    private String avatar;
    private String summary;

    @Enumerated
    private Status status;

    @Enumerated
    private Status commentStatus;
    private String lastLoginIp;
    private LocalDateTime lastLoginTime;

    public Member login(String ip) {
        lastLoginIp = ip;
        lastLoginTime = LocalDateTime.now();
        return this;
    }
}