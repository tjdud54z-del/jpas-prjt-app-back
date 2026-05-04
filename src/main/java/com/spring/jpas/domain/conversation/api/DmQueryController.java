
package com.spring.jpas.domain.conversation.api;

import com.spring.jpas.domain.conversation.query.application.DmQueryService;
import com.spring.jpas.domain.conversation.query.dto.DmConversationListItem;
import com.spring.jpas.domain.conversation.query.dto.DmMessageRow;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

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
    @GetMapping("/conversations/{id}/messages")
    public List<DmMessageRow> messages(
            Principal principal,
            @PathVariable Long id,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(required = false) Long cursorMessageId
    ) {
        String me = principal.getName();
        return queryService.getMessages(me, id, size, cursorMessageId);
    }
}
