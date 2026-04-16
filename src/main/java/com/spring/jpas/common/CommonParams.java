package com.spring.jpas.common;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonParams {
    /** 사번 */
    private String employeeNo;
    /** 이름 */
    private String name;
    /** 재직여부 (Y/N) */
    private String activeYn;
    /** 입사일 **/
    private String hireDate;
}
