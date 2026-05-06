package com.spring.jpas.domain.user.command.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDto {
    private String userNo;
    private String password;
    private String name;
    private String email;
    private String addressMain;
    private String addressSub;
    private LocalDate brithDate;
}
