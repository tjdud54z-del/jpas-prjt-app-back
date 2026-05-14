package com.spring.jpas.domain.cmmnCd.command.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class CmmnCdDtlId implements Serializable {

    private String commonCode;
    private String commonCodeDtl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CmmnCdDtlId)) return false;
        CmmnCdDtlId that = (CmmnCdDtlId) o;
        return Objects.equals(commonCode, that.commonCode)
            && Objects.equals(commonCodeDtl, that.commonCodeDtl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commonCode, commonCodeDtl);
    }

}
