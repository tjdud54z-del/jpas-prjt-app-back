package com.spring.jpas.websoket;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebSocketInterceptorConfig implements WebSocketMessageBrokerConfigurer {

    private final StompAuthInterceptor stompAuthInterceptor;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // ✅ STOMP 메시지 들어오기 전에 가로챔
        registration.interceptors(stompAuthInterceptor);
    }
}

