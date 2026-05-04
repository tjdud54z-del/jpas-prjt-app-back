
package com.spring.jpas.domain.conversation.api;

import com.spring.jpas.domain.conversation.command.dto.DmDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class DmController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/dm.send")
    public void sendDm(DmDto message, Principal principal) {

        if (message.getReceiverUserId() == null) {
            throw new IllegalArgumentException("receiverUserId is required");
        }

        // ✅ WebSocket Principal = 로그인 ID (예: "admin", "user25")
        String senderUserId = principal.getName();
        String receiverUserId = message.getReceiverUserId();

        message.setSenderUserId(senderUserId);
        message.setSentAt(LocalDateTime.now());

        // ✅ 상대방에게 전송
        messagingTemplate.convertAndSendToUser(
                receiverUserId,
                "/queue/dm",
                message
        );

        // ✅ 보낸 사람에게도 echo (내 화면용)
        messagingTemplate.convertAndSendToUser(
                senderUserId,
                "/queue/dm",
                message
        );
    }
}
