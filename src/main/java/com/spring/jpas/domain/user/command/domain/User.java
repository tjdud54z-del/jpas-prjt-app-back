
package com.spring.jpas.domain.user.command.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "jpas_tb_user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    /** 직원 PK */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    /** 사번 */
    @Column(name = "USER_NO", length = 20, nullable = false, unique = true)
    private String userNo;

    /** 사번 */
    @Column(name = "PASSWORD", length = 20, nullable = false)
    private String password;

    /** 이름 */
    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    /** 이메일 */
    @Column(name = "EMAIL", length = 100, nullable = false, unique = true)
    private String email;

    /** 부서코드 */
    @Column(name = "ADDRESS_MAIN", length = 1000, nullable = false)
    private String addressMain;

    /** 직급코드 */
    @Column(name = "ADDRESS_SUB", length = 1000, nullable = false)
    private String addressSub;

    /** 입사일 */
    @Column(name = "BIRTH_DATE", nullable = false)
    private LocalDate birthDate;

    /** 재직 여부 */
    @Column(name = "ACTIVE_YN", length = 1, nullable = false)
    private String activeYn;

    /** 생성일시 */
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** 수정일시 */
    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    /** 성별 */
    @Column(name = "GENDER_FLAG", nullable = false)
    private String genderFlag;

    /** 전화번호 */
    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    /** 프로필 */
    @Column(name = "PROFILE_IMAGE_PATH")
    private String profileImagePath;

    /* ===========================
       JPA Lifecycle
     =========================== */
    @PrePersist
    public void onCreate() {
        this.activeYn = "Y";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /* ===========================
       생성자 (등록용)
     =========================== */
    public User(
            String userNo,
            String password,
            String name,
            String email,
            String addressMain,
            String addressSub,
            LocalDate birthDate,
            String genderFlag,
            String phoneNumber
    ) {
        this.userNo = userNo;
        this.password = password;
        this.name = name;
        this.email = email;
        this.addressMain = addressMain;
        this.addressSub = addressSub;
        this.birthDate = birthDate;
        this.genderFlag = genderFlag;
        this.phoneNumber = phoneNumber;
    }


    /* create 팩토리 */
    public static User create(
            String userNo,
            String password,
            String name,
            String email,
            String addressMain,
            String addressSub,
            LocalDate birthDate,
            String genderFlag,
            String phoneNumber
    ) {
        return new User(userNo, password, name, email, addressMain, addressSub, birthDate, genderFlag, phoneNumber);
    }

    /* update 도메인 메서드 */
    public void update(
            String password,
            String name,
            String email,
            String addressMain,
            String addressSub,
            LocalDate birthDate,
            String genderFlag,
            String phoneNumber
    ) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.addressMain = addressMain;
        this.addressSub = addressSub;
        this.birthDate = birthDate;
        this.genderFlag = genderFlag;
        this.phoneNumber = phoneNumber;
    }


    /* ===========================
       비즈니스 메서드
     =========================== */

    /** 퇴사 처리 */
    public void retire() {
        this.activeYn = "N";
    }

    /** 복직 처리 */
    public void restore() {
        this.activeYn = "Y";
    }

}
