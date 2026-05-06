package com.spring.jpas.domain.user.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String userNo;
    private String name;
    private String email;
    private String birthDate;
    private String addressMain;
    private String addressSub;
    private String activeYn;
}