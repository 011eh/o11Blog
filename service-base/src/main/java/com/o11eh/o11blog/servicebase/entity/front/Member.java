package com.o11eh.o11blog.servicebase.entity.front;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.o11eh.o11blog.servicebase.entity.BaseEntry;
import com.o11eh.o11blog.servicebase.enums.Status;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@DynamicInsert
@DynamicUpdate
@Entity(name = "front_member")
public class Member extends BaseEntry {

    @Email
    private String email;

    @JsonIgnore
    private String password;

    @Length(min = 2, max = 16)
    private String nickName;

    private String avatar;
    private String summary;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Article> articles;

    @Enumerated
    private Status status;

    @Enumerated
    private Status commentStatus;
    private String lastLoginIp;
    private LocalDateTime lastLoginTime;

    public void login(String ip) {
        lastLoginIp = ip;
        lastLoginTime = LocalDateTime.now();
    }
}
