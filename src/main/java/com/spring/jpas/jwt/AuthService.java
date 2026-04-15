
// AuthService.java
package com.spring.jpas.jwt;


import com.spring.jpas.jpa.entity.Employee;
import com.spring.jpas.jpa.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(String employeeNo, String password) {

        Employee emp = employeeRepository.findByEmployeeNo(employeeNo)
                .orElseThrow(() -> new IllegalArgumentException("계정이 존재하지 않습니다."));

        // 퇴사자 차단 (강력 추천)
        if (!"Y".equals(emp.getActiveYn())) {
            throw new IllegalArgumentException("퇴사자입니다.");
        }

        if (!passwordEncoder.matches(password, emp.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }

        String token = jwtTokenProvider.createAccessToken(employeeNo);

        UserInfoDto userInfoDto = new UserInfoDto(
                emp.getEmployeeId(),
                emp.getEmployeeNo(),
                emp.getName(),
                emp.getEmail(),
                emp.getDepartmentCode(),
                emp.getPositionCode(),
                emp.getActiveYn()
        );

        return new LoginResponse(
                token,
                "Bearer",
                jwtTokenProvider.getAccessTokenExpireSeconds(),
                userInfoDto
        );
    }
}

