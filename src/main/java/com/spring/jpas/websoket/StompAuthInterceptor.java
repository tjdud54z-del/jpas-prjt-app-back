package com.spring.jpas.websoket;


import com.spring.jpas.jwt.JwtTokenProvider;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.List;


@Component
public class StompAuthInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public StompAuthInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String auth = accessor.getFirstNativeHeader("Authorization"); // "Bearer xxx"
            if (auth == null || !auth.startsWith("Bearer ")) {
                try {
                    throw new AccessDeniedException("NO_TOKEN");
                } catch (AccessDeniedException e) {
                    throw new RuntimeException(e);
                }
            }

            String token = auth.substring(7);
            if (!jwtTokenProvider.validate(token)) {
                try {
                    throw new AccessDeniedException("INVALID_TOKEN");
                } catch (AccessDeniedException e) {
                    throw new RuntimeException(e);
                }
            }

            // 토큰에서 employeeId 추출
            // Long employeeId = Long.valueOf(jwtTokenProvider.getEmployeeId(token));
            String userId = jwtTokenProvider.getUserId(token);

            // user destination 매핑용: Principal name을 employeeId 문자열로 세팅
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(String.valueOf(userId), null, List.of());
            accessor.setUser(authentication);
        }

        return message;
    }
}



