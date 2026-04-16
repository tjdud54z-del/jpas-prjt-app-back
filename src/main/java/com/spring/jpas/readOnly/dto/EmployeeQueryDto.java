package com.spring.jpas.readOnly.dto;

import lombok.Getter;

@Getter
public class EmployeeQueryDto {

    private Long employeeId;
    private String employeeNo;
    private String name;
    private String email;
    private String hireDate;
    private String departmentCode;
    private String departmentName;
    private String positionCode;
    private String positionName;
    private String activeYn;

}