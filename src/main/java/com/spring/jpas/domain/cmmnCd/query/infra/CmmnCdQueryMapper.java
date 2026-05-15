package com.spring.jpas.domain.cmmnCd.query.infra;

import com.spring.jpas.domain.cmmnCd.query.dto.CmmnCdDto;
import com.spring.jpas.global.common.CommonParams;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 공통코드 쿼리를 연동하기 위한 인터페이스
 * @author 정서영
 * @since 2026-05-11
 * @version 1.0
 */
@Mapper
public interface CmmnCdQueryMapper {

    /**
     * 공통코드 검색조건에 따라 조회하는 메서드
     * @param commonParams dto 공통파라미터
     * @return List<CmmnCdDto>
     */
    List<CmmnCdDto> searchCmmnCdList(CommonParams commonParams);

    /**
     * 서브공통코드 검색조건에 따라 조회하는 메서드
     * @param commonParams dto 공통파라미터
     * @return List<CmmnCdDto>
     */
    List<CmmnCdDto> searchCmmnCdDtlList(CommonParams commonParams);

    /**
     * selectbox의 값과 명의 정보 공통코드에 따라 조회
     * @param commonParams dto 공통파라미터
     * @return List<CmmnCdDto>
     */
    List<CmmnCdDto> searchCmmnCdSelectOption(CommonParams commonParams);

}
