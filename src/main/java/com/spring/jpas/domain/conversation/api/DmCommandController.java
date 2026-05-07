package com.spring.jpas.domain.conversation.api;


import com.spring.jpas.domain.conversation.command.application.DmCommandService;
import com.spring.jpas.domain.conversation.command.domain.DmMessage;
import com.spring.jpas.domain.conversation.command.dto.DmDto;
import com.spring.jpas.domain.conversation.command.infra.DmMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dm")
public class DmCommandController {

    private final DmCommandService commandService;
    private final SimpMessagingTemplate messagingTemplate;

    /** 대화방 열기(없으면 생성) */
    @PostMapping("/conversations")
    public Map<String, Long> open(@RequestBody DmDto request, Principal principal) {
        Long me = request.getSenderId();
        Long peer = request.getReceiverId();
        Long convId = commandService.openConversation(me, peer);
        return Map.of("conversationId", convId);
    }

    /** DM 메시지 전송 */
    @PostMapping("/messages")
    public void sendMessage(@RequestBody DmDto request, Principal principal) {

        // 프론트 localStorage JWT 계정 ID
        Long senderId = request.getSenderId();

        // DM 메세지 저장
        DmMessage saveData = commandService.send(
                senderId,
                request.getReceiverId(),
                request.getBody(),
                request.getMsgType()
        );

        // 현재 대화방에서 보낸사람은 읽음처리로 처리 --> 받은 사람은 처리하면 안됨
        commandService.markConversationRead(saveData.getConversationId(), senderId);

        // WebSocket Principal = 로그인 ID (예: "admin", "user25")
        String senderUserNo = principal.getName();
        String receiverUserNo = request.getReceiverUserId();
        String content = request.getBody();

        // WebSocket 양식에 맞게 셋팅
        request.setSenderUserId(senderUserNo);
        request.setSentAt(LocalDateTime.now());
        request.setContent(content);

        // 상대방에게 전송
        messagingTemplate.convertAndSendToUser(
                receiverUserNo,
                "/queue/dm",
                request
        );

        // 보낸 사람에게도 echo (내 화면용)
        messagingTemplate.convertAndSendToUser(
                senderUserNo,
                "/queue/dm",
                request
        );

    }

    /** 대화방 읽음 처리 */
    @PostMapping("/conversations/{conversationId}/read")
    public void read(@PathVariable Long conversationId,
                     @RequestBody Map<String, Long> body) {
        Long userId = body.get("userId"); // 로그인 사용자
        commandService.markConversationRead(conversationId, userId);
        // commandService.markConversationReadAndBroadcast(conversationId, userId);
    }



}
