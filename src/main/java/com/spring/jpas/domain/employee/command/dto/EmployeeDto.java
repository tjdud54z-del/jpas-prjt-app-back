package com.spring.jpas.domain.employee.command.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeDto {
    private String userNo;
    private String password;
    private String name;
    private String email;
    private String departmentCode;
    private String positionCode;
    private LocalDate hireDate;
}
