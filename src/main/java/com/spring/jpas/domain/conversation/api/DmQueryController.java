
package com.spring.jpas.domain.conversation.api;

import com.spring.jpas.domain.conversation.query.application.DmQueryService;
import com.spring.jpas.domain.conversation.query.dto.DmConversationListItem;
import com.spring.jpas.domain.conversation.query.dto.DmMessageRow;
import com.spring.jpas.global.common.CommonParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dm")
public class DmQueryController {

    private final DmQueryService queryService;

    /** (R) 대화 목록(inbox) - 커서 페이징 */
    @GetMapping("/conversations")
    public List<DmConversationListItem> inbox(
            Principal principal,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) LocalDateTime cursorAt,
            @RequestParam(required = false) Long cursorId
    ) {
        String me = principal.getName();
        return queryService.getInbox(userId, me, size, cursorAt, cursorId);
    }

    /** (R) 메시지 히스토리 - 커서(키셋) 페이징 */
    @PostMapping("/conversations/messages")
    public Map<String, Object> messages(Principal principal, @RequestBody CommonParams commonParams) {
        String userNo = principal.getName();
        Long userId = commonParams.getUserId();
        Long conversationId = commonParams.getConversationId();
        Integer size = commonParams.getSize();
        Long cursorMessageId = commonParams.getCursorMessageId();

        List<DmMessageRow> messages = queryService.getMessages(userId, userNo, conversationId, size, cursorMessageId); // 메세지 히스토리 리스트

        Long peerLastReadMessageId = queryService.getPeerLastReadMessageId(userId, conversationId); // 상대방의 마지막 읽은 메시지 ID

        return Map.of("messages", messages,"peerLastReadMessageId", peerLastReadMessageId);

    }

}
