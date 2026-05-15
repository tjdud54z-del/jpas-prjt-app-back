package com.spring.jpas.domain.cmmnCd.api;

import com.spring.jpas.domain.cmmnCd.query.application.CmmnCdQueryService;
import com.spring.jpas.domain.cmmnCd.query.dto.CmmnCdDto;
import com.spring.jpas.global.common.CommonParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 공통코드 URL를 정의하는 클래스
 * @author 정서영
 * @since 2026-05-11
 * @version 1.0
 */
@RestController
@RequestMapping("/api/query/cmmnCd")
public class CmmnCdQueryController {

    // Service import, basic controller
    private final CmmnCdQueryService cmmnCdQueryService;
    public CmmnCdQueryController(CmmnCdQueryService cmmnCdQueryService) { this.cmmnCdQueryService = cmmnCdQueryService; }

    /**
     * 공통코드 정보를 검색조건에 따라 조회
     * @param commonParams dto 공통파라미터
     * @return List<CmmnCdDto>
     */
    @PostMapping("/cmmnCdList")
    public List<CmmnCdDto> searchCmmnCdList(@RequestBody CommonParams commonParams) {
        return cmmnCdQueryService.searchCmmnCdList(commonParams);
    }

    /**
     * 서브공통코드 정보를 검색조건에 따라 조회
     * @param commonParams dto 공통파라미터
     * @return List<CmmnCdDto>
     */
    @PostMapping("/cmmnCdDtlList")
    public List<CmmnCdDto> searchCmmnCdDtlList(@RequestBody CommonParams commonParams) {
        return cmmnCdQueryService.searchCmmnCdDtlList(commonParams);
    }

    /**
     * selectbox의 값과 명의 정보 공통코드에 따라 조회
     * @param commonParams dto 공통파라미터
     * @return List<CmmnCdDto>
     */
    @PostMapping("/selectOption")
    public List<CmmnCdDto> searchCmmnCdSelectOption(@RequestBody CommonParams commonParams) {
        return cmmnCdQueryService.searchCmmnCdSelectOption(commonParams);
    }

}
