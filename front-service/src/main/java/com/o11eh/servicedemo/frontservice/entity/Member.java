package com.o11eh.servicedemo.frontservice.entity;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity(name = "front_member")
public class Member extends BaseEntry {

    private String username;
    private String password;
}
