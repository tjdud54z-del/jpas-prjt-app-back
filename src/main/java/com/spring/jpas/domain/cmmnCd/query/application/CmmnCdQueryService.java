package com.spring.jpas.domain.cmmnCd.query.application;

import com.spring.jpas.domain.cmmnCd.query.dto.CmmnCdDto;
import com.spring.jpas.domain.cmmnCd.query.infra.CmmnCdQueryMapper;
import com.spring.jpas.global.common.CommonParams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 공통코드 서비스 및 로직을 정의하는 클래스
 * @author 정서영
 * @since 2026-05-11
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class CmmnCdQueryService {

    // Mapper import, basic controller
    private final CmmnCdQueryMapper cmmnCdQueryMapper;
    public CmmnCdQueryService(CmmnCdQueryMapper cmmnCdQueryMapper) { this.cmmnCdQueryMapper = cmmnCdQueryMapper; }

    /**
     * 공통코드 검색조건에 따라 조회하는 메서드
     * @param commonParams dto 공통파라미터
     * @return List<CmmnCdDto>
     */
    public List<CmmnCdDto> searchCmmnCdList(CommonParams commonParams) {
        return cmmnCdQueryMapper.searchCmmnCdList(commonParams);
    }

     /**
     * 서브공통코드 검색조건에 따라 조회하는 메서드
     * @param commonParams dto 공통파라미터
     * @return List<CmmnCdDto>
     */
    public List<CmmnCdDto> searchCmmnCdDtlList(CommonParams commonParams) {
        return cmmnCdQueryMapper.searchCmmnCdDtlList(commonParams);
    }

}
