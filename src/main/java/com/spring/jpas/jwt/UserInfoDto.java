package com.spring.jpas.jwt;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private Long employeeId;
    private String employeeNo;
    private String name;
    private String email;
    private String departmentCode;
    private String positionCode;
    private String activeYn;
}
