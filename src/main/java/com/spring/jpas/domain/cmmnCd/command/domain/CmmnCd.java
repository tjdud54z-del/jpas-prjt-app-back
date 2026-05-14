package com.spring.jpas.domain.cmmnCd.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "jpas_tb_cmmn_cd")
@Getter
@Setter
@NoArgsConstructor
public class CmmnCd {

    @Id
    @Column(name = "COMMON_CODE", length = 50, nullable = false)
    private String commonCode;

    @Column(name = "COMMON_CODE_NAME", length = 100, nullable = false)
    private String commonCodeName;

    @Column(name = "ACTIVE_YN", length = 1, nullable = false)
    private String activeYn; // 'Y' | 'N'

    @Column(name = "SORT_ORDER")
    private Integer sortOrder; // default 0 (DB)

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Column(name = "ATTR1", length = 100)
    private String attr1;

    @Column(name = "ATTR2", length = 100)
    private String attr2;

    @Column(name = "ATTR3", length = 100)
    private String attr3;

    @Column(name = "ATTR4", length = 100)
    private String attr4;

    @Column(name = "ATTR5", length = 100)
    private String attr5;

    @Column(name = "ATTR6", length = 100)
    private String attr6;

    @Column(name = "ATTR7", length = 100)
    private String attr7;

    @Column(name = "ATTR8", length = 100)
    private String attr8;

    @Column(name = "ATTR9", length = 100)
    private String attr9;

    @Column(name = "ATTR10", length = 100)
    private String attr10;

    // DB가 자동으로 채우는 컬럼 (DEFAULT/ON UPDATE)
    @Column(name = "CREATED_AT", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false, insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Column(name = "CREATED_USER_ID", nullable = false)
    private Long createdUserId;

    @Column(name = "UPDATED_USER_ID", nullable = false)
    private Long updatedUserId;

}
