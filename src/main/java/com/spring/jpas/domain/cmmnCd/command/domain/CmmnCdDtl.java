package com.spring.jpas.domain.cmmnCd.command.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "jpas_tb_cmmn_cd_dtl")
@Getter
@Setter
@NoArgsConstructor
@IdClass(CmmnCdDtlId.class)
public class CmmnCdDtl {

    @Id
    @Column(name = "COMMON_CODE", length = 50)
    private String commonCode;

    @Id
    @Column(name = "COMMON_CODE_DTL", length = 50)
    private String commonCodeDtl;

    @Column(name = "COMMON_CODE_DTL_NAME", length = 100, nullable = false)
    private String commonCodeDtlName;

    @Column(name = "ACTIVE_YN", length = 1, nullable = false)
    private String activeYn;

    @Column(name = "SORT_ORDER")
    private Integer sortOrder;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Column(name = "ATTR1")
    private String attr1;

    @Column(name = "ATTR2")
    private String attr2;

    @Column(name = "ATTR3")
    private String attr3;

    @Column(name = "ATTR4")
    private String attr4;

    @Column(name = "ATTR5")
    private String attr5;

    @Column(name = "ATTR6")
    private String attr6;

    @Column(name = "ATTR7")
    private String attr7;

    @Column(name = "ATTR8")
    private String attr8;

    @Column(name = "ATTR9")
    private String attr9;

    @Column(name = "ATTR10")
    private String attr10;

    @Column(name = "CREATED_AT", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Column(name = "CREATED_USER_ID", nullable = false)
    private Long createdUserId;

    @Column(name = "UPDATED_USER_ID", nullable = false)
    private Long updatedUserId;


}
