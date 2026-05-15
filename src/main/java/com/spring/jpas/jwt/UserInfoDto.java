package com.spring.jpas.jwt;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private Long userId;
    private String userNo;
    private String name;
    private String email;
    private String activeYn;
    private String profileImagePath;
    private String logMessage;
}
