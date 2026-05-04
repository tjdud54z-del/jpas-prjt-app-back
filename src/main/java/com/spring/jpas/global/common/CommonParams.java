package com.spring.jpas.global.common;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class CommonParams {
   /*
    * =========================
    * 파라미터 단일 사용
    * =========================
    */

    private Long employeeId; // 직원번호(PK)
    private String employeeNo; // 사번
    private Long userId;
    private String userNo;
    private String password; // 비밀번호
    private String name; // 이름
    private String email; // 이메일
    private String departmentCode; // 부서코드
    private String positionCode; // 직급코드
    private String hireDate; // 입사일
    private String activeYn; // 재직여부(Y/N)

    /*
    * =========================
    * 파라미터 다중 사용
    * =========================
    */

    private List<Long> userIds;
}
