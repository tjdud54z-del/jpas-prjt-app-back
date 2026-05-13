package com.spring.jpas.domain.user.api;

import com.spring.jpas.global.common.CommonParams;
import com.spring.jpas.domain.user.query.dto.UserDto;
import com.spring.jpas.domain.user.query.application.UserQueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 직원정보 URL를 정의하는 클래스
 * @author 정서영
 * @since 2026-04-10
 * @version 1.0
 */
@RestController
@RequestMapping("/api/query/users")
public class UserQueryController {

    // Service import, basic controller
    private final UserQueryService userQueryService;
    public UserQueryController(UserQueryService userQueryService) { this.userQueryService = userQueryService; }

    /**
     * 직원정보 전체조회
     * @return List<UserDto>
     */
    @GetMapping
    public List<UserDto> getUserList() {
        return userQueryService.findAll();
    }

    /**
     * 직원정보 직원아이디로 조회
     * @param id string 직원아이디
     * @return UserDto
     */
    @GetMapping("/{id}")
    public UserDto getFindUserId(@PathVariable Long id) {
        return userQueryService.findById(id);
    }

    /**
     * 직원정보 검색조건에 따라 조회
     * @param commonParams dto 공통파라미터
     * @return List<UserDto>
     */
    @PostMapping("/search")
    public List<UserDto> serachUserList(@RequestBody CommonParams commonParams) {
        return userQueryService.serachUserList(commonParams);
    }
}
