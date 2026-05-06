package com.spring.jpas.domain.user.api;

import com.spring.jpas.global.common.CommonParams;
import com.spring.jpas.domain.user.query.dto.UserDto;
import com.spring.jpas.domain.user.query.application.UserQueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 직원정보에 대한 URL를 정의하는 클래스
 * @author 정서영
 * @since 2025-04-10
 * @version 1.0
 */
@RestController
@RequestMapping("/api/query/users")
public class UserQueryController {

    private final UserQueryService service;

    public UserQueryController(UserQueryService service) {
        this.service = service;
    }

    /**
     * 직원의 정보를 전체조회
     * @return List<UserDto>
     */
    @GetMapping
    public List<UserDto> getUsers() {
        return service.findAll();
    }

    /**
     * 직원의 정보를 직원아이디로 조회
     * @param id string 직원아이디
     * @return UserDto
     */
    @GetMapping("/{id}")
    public UserDto getFindUserId(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * 직원의 정보를 검색조건에 따라 조회
     * @param commonParams dto 공통파라미터
     * @return List<UserDto>
     */
    @PostMapping("/search")
    public List<UserDto> searchUsers(@RequestBody CommonParams commonParams) {
        return service.searchUsers(commonParams);
    }
}
