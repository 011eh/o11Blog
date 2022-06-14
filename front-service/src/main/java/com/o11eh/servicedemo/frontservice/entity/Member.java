package com.o11eh.servicedemo.frontservice.entity;

import com.o11eh.servicedemo.servicebase.enums.Status;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Data
@Entity(name = "front_member")
public class Member extends BaseEntry {

    @Length(min = 2, max = 32)
    private String username;

    @Length(min = 8, max = 16)
    private String password;

    @Length(min = 2, max = 16)
    private String nickName;

    @Email
    private String email;
    private String avatar;
    private String summary;

    @Enumerated
    private Status status;

    @Enumerated
    private Status commentStatus;
    private LocalDateTime lastLoginIp;
    private LocalDateTime lastLoginTime;
}
