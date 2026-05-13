package com.spring.jpas.domain.user.query.application;

import com.spring.jpas.domain.user.query.dto.UserDto;
import com.spring.jpas.global.common.CommonParams;
import com.spring.jpas.domain.user.query.infra.UserQueryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 직원정보 서비스 및 로직을 정의하는 클래스
 * @author 정서영
 * @since 2026-04-10
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class UserQueryService {

    // Mapper import, basic controller
    private final UserQueryMapper userQueryMapper;
    public UserQueryService(UserQueryMapper userQueryMapper) {
        this.userQueryMapper = userQueryMapper;
    }

    /**
     * 직원정보 전체조회하는 메서드
     * @return List<UserDto>
     */
    public List<UserDto> findAll() {
        return userQueryMapper.findAll();
    }

    /**
     * 직원정보 직원아이디로 조회하는 메서드
     * @param id string 직원아이디
     * @return UserDto
     */
    public UserDto findById(Long id) {
        return userQueryMapper.findById(id);
    }

    /**
     * 직원정보 검색조건에 따라 조회하는 메서드
     * @param commonParams dto 공통파라미터
     * @return List<UserDto>
     */
    public List<UserDto> serachUserList(CommonParams commonParams) {
        return userQueryMapper.serachUserList(commonParams);
    }

}

