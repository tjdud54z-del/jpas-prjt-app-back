package com.spring.jpas.domain.cmmnCd.command.infra;

import com.spring.jpas.domain.cmmnCd.command.domain.CmmnCd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmmnCdRepository extends JpaRepository<CmmnCd, String> {
}
