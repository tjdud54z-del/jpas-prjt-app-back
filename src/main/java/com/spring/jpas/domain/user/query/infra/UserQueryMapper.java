
package com.spring.jpas.domain.user.query.infra;

import com.spring.jpas.global.common.CommonParams;
import com.spring.jpas.domain.user.query.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 직원정보에 대한 쿼리를 연동하기 위한 인터페이스
 * @author 정서영
 * @since 2025-04-10
 * @version 1.0
 */
@Mapper
public interface UserQueryMapper {

    /**
     * 직원의 정보를 전체조회하는 메서드
     * @return List<UserDto>
     */
    List<UserDto> findAll();

    /**
     * 직원의 정보를 직원아이디로 조회하는 메서드
     * @param id string 직원아이디
     * @return UserDto
     */
    UserDto findById(Long id);

    /**
     * 직원의 정보를 검색조건에 따라 조회하는 메서드
     * @param commonParams dto 공통파라미터
     * @return List<UserDto>
     */
    List<UserDto> searchUsers(CommonParams commonParams);

}
