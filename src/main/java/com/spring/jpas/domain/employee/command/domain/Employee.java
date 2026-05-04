
package com.spring.jpas.domain.employee.command.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "JPAS_TB_USER")
@Getter
@NoArgsConstructor
public class Employee {

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
    @Column(name = "DEPARTMENT_CODE", length = 50, nullable = false)
    private String departmentCode;

    /** 직급코드 */
    @Column(name = "POSITION_CODE", length = 50, nullable = false)
    private String positionCode;

    /** 입사일 */
    @Column(name = "HIRE_DATE", nullable = false)
    private LocalDate hireDate;

    /** 재직 여부 */
    @Column(name = "ACTIVE_YN", length = 1, nullable = false)
    private String activeYn;

    /** 생성일시 */
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** 수정일시 */
    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

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
    public Employee(
            String userNo,
            String password,
            String name,
            String email,
            String departmentCode,
            String positionCode,
            LocalDate hireDate
    ) {
        this.userNo = userNo;
        this.password = password;
        this.name = name;
        this.email = email;
        this.departmentCode = departmentCode;
        this.positionCode = positionCode;
        this.hireDate = hireDate;
    }


    /* create 팩토리 */
    public static Employee create(
            String userNo,
            String password,
            String name,
            String email,
            String departmentCode,
            String positionCode,
            LocalDate hireDate
    ) {
        return new Employee(userNo, password, name, email, departmentCode, positionCode, hireDate);
    }

    /* update 도메인 메서드 */
    public void update(
            String password,
            String name,
            String email,
            String departmentCode,
            String positionCode,
            LocalDate hireDate
    ) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.departmentCode = departmentCode;
        this.positionCode = positionCode;
        this.hireDate = hireDate;
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
