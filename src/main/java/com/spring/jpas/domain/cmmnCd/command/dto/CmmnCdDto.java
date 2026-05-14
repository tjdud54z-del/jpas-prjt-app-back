package com.spring.jpas.domain.cmmnCd.command.dto;

import com.spring.jpas.global.common.CrudType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CmmnCdDto {

    private CrudType crudType; // I/U/D
    private String commonCode;
    private String commonCodeName;
    private String activeYn;
    private Integer sortOrder;
    private String description;
    private String attr1;
    private String attr2;
    private String attr3;
    private String attr4;
    private String attr5;
    private String attr6;
    private String attr7;
    private String attr8;
    private String attr9;
    private String attr10;
    private Long createdUserId;
    private LocalDateTime createdAt;
    private Long updatedUserId;
    private LocalDateTime updatedAt;

}
