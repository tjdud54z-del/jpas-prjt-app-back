package com.spring.jpas.global.common;


import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommonParams {
    /** 직원번호(PK) */
    private Long employeeId;
    /** 사번 */
    private String employeeNo;
    /** 유저 PK */
    private Long userId;
    /** 유저 ID */
    private String userNo;
    /** 비밀번호 */
    private String password;
    /** 이름 */
    private String name;
    /** 이메일 */
    private String email;
    /** 메인주소 */
    private String addressMain;
    /** 상세주소 */
    private String addressSub;
    /** 생년월일 */
    private String birthDate;
    /** 탈퇴여부 (Y/N) */
    private String activeYn;
    /** 성별 (M/W) */
    private String genderFlag;
    /** 대화방 ID */
    private Long conversationId;
    /** limit 사이즈 (쿼리 조회용) */
    private Integer size;
    /** 커서 페이징 메시지 ID */
    private Long cursorMessageId;
    /** 유저 ID 리스트 */
    private List<Long> userIds;
    /** 공통코드 */
    private String commonCode;
    /** 공통코드명 */
    private String commonCodeName;
    /** 서브공통코드 */
    private String commonCodeDtl;
    /** 서브공통코드명 */
    private String commonCodeDtlName;
}
