package com.spring.jpas.domain.employee.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor   // 매우 중요
@AllArgsConstructor
public class EmployeeDto {
    private Long userId;
    private String userNo;
    private String name;
    private String email;
    private String hireDate;
    private String departmentCode;
    private String departmentName;
    private String positionCode;
    private String positionName;
    private String activeYn;
}