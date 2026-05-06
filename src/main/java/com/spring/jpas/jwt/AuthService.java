
// AuthService.java
package com.spring.jpas.jwt;


import com.spring.jpas.domain.user.command.domain.User;
import com.spring.jpas.domain.user.command.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(String employeeNo, String password) {
        
        String logMessage = "";
        
        User emp = userRepository.findByUserNo(employeeNo)
                .orElseThrow(() -> new IllegalArgumentException("계정이 존재하지 않습니다."));

        // 퇴사자 차단 (강력 추천)
        if (!"Y".equals(emp.getActiveYn())) {
            logMessage = "퇴사자입니다.";
            throw new IllegalArgumentException("퇴사자입니다.");
        }

        if (!passwordEncoder.matches(password, emp.getPassword())) {
            logMessage = "계정이나 비밀번호가 틀립니다.";
            throw new IllegalArgumentException("계정이나 비밀번호가 틀립니다.");
            
        }

        String token = jwtTokenProvider.createAccessToken(employeeNo);

        UserInfoDto userInfoDto = new UserInfoDto(
                emp.getUserId(),
                emp.getUserNo(),
                emp.getName(),
                emp.getEmail(),
                emp.getActiveYn(),
                logMessage
        );

        return new LoginResponse(
                token,
                "Bearer",
                jwtTokenProvider.getAccessTokenExpireSeconds(),
                userInfoDto
        );
    }
}

