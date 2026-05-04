package com.spring.jpas.domain.conversation.api;


import com.spring.jpas.domain.conversation.command.application.DmCommandService;
import com.spring.jpas.domain.conversation.command.domain.DmMessage;
import com.spring.jpas.domain.conversation.command.infra.DmMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dm")
public class DmCommandController {

    private final DmCommandService commandService;

    /** 대화방 열기(없으면 생성) */
    @PostMapping("/conversations")
    public Map<String, Long> open(@RequestBody Map<String, Long> body, Principal principal) {
        Long me = Long.valueOf(principal.getName());
        Long peer = body.get("peerEmployeeId");
        Long convId = commandService.openConversation(me, peer);
        return Map.of("conversationId", convId);
    }

    /** 읽음 처리 */
    @PostMapping("/conversations/{id}/read")
    public void read(@PathVariable Long conversationId, @RequestBody Map<String, Long> body, Principal principal) {
        Long me = Long.valueOf(principal.getName());
        Long lastReadMessageId = body.get("lastReadMessageId");
        commandService.markRead(conversationId, me, lastReadMessageId);
    }


}
