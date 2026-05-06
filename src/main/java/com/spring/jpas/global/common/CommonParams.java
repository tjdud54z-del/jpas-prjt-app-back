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
    private Long userId; // 유저PK
    private String userNo; // 유저ID
    private String password; // 비밀번호
    private String name; // 이름
    private String email; // 이메일
    private String addressMain; // 메인주소
    private String addressSub; // 상세주소
    private String birthDate; // 생년월일
    private String activeYn; // 탈퇴여부(Y/N)

    /*
    * =========================
    * 파라미터 다중 사용
    * =========================
    */

    private List<Long> userIds;
}
