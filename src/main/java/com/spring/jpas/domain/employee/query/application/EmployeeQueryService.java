package com.spring.jpas.domain.employee.query.application;

import com.spring.jpas.domain.employee.query.dto.EmployeeDto;
import com.spring.jpas.global.common.CommonParams;
import com.spring.jpas.domain.employee.query.infra.EmployeeQueryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 직원정보에 대한 서비스 및 로직을 정의하는 클래스
 * @author 정서영
 * @since 2025-04-10
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class EmployeeQueryService {

    private final EmployeeQueryMapper mapper;

    public EmployeeQueryService(EmployeeQueryMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 직원의 정보를 전체조회하는 메서드
     * @return List<EmployeeQueryDto>
     */
    public List<EmployeeDto> findAll() {
        return mapper.findAll();
    }

    /**
     * 직원의 정보를 직원아이디로 조회하는 메서드
     * @param id string 직원아이디
     * @return EmployeeQueryDto
     */
    public EmployeeDto findById(Long id) {
        return mapper.findById(id);
    }

    /**
     * 직원의 정보를 검색조건에 따라 조회하는 메서드
     * @param commonParams dto 공통파라미터
     * @return List<EmployeeQueryDto>
     */
    public List<EmployeeDto> searchEmployees(CommonParams commonParams) {
        return mapper.searchEmployees(commonParams);
    }

}

