package com.spring.jpas.domain.user.command.infra;

import com.spring.jpas.domain.user.command.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNo(String userNo);

    @Query("""
        select max(e.userNo)
        from   User e
        where  e.userNo like 'user%'
    """)
    String findMaxUserNo();

}