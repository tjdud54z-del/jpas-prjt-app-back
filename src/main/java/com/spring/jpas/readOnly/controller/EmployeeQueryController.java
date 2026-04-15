package com.spring.jpas.readOnly.controller;

import com.spring.jpas.common.CommonParams;
import com.spring.jpas.readOnly.dto.EmployeeQueryDto;
import com.spring.jpas.readOnly.service.EmployeeQueryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 직원정보에 대한 URL를 정의하는 클래스
 * @author 정서영
 * @since 2025-04-10
 * @version 1.0
 */
@RestController
@RequestMapping("/api/query/employees")
public class EmployeeQueryController {

    private final EmployeeQueryService service;

    public EmployeeQueryController(EmployeeQueryService service) {
        this.service = service;
    }

    /**
     * 직원의 정보를 전체조회
     * @return List<EmployeeQueryDto>
     */
    @GetMapping
    public List<EmployeeQueryDto> getEmployees() {
        return service.findAll();
    }

    /**
     * 직원의 정보를 직원아이디로 조회
     * @param id string 직원아이디
     * @return EmployeeQueryDto
     */
    @GetMapping("/{id}")
    public EmployeeQueryDto getEmployee(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * 직원의 정보를 검색조건에 따라 조회
     * @param commonParams dto 공통파라미터
     * @return List<EmployeeQueryDto>
     */
    @PostMapping("/search")
    public List<EmployeeQueryDto> searchEmployees(@RequestBody CommonParams commonParams) {
        return service.searchEmployees(commonParams);
    }
}
