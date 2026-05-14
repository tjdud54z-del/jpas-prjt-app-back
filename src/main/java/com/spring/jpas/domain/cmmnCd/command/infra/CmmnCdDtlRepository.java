package com.spring.jpas.domain.cmmnCd.command.infra;

import com.spring.jpas.domain.cmmnCd.command.domain.CmmnCdDtl;
import com.spring.jpas.domain.cmmnCd.command.domain.CmmnCdDtlId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmmnCdDtlRepository extends JpaRepository<CmmnCdDtl, CmmnCdDtlId> {

    // 공통코드 기준 전체 삭제 (Cascade용)
    void deleteByCommonCode(String commonCode);

}
