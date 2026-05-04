
package com.spring.jpas.domain.conversation.query.application;

import com.spring.jpas.domain.conversation.query.dto.DmConversationListItem;
import com.spring.jpas.domain.conversation.query.dto.DmMessageRow;
import com.spring.jpas.domain.conversation.query.infra.DmQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DmQueryService {

    private final DmQueryMapper mapper;

    @Transactional(readOnly = true)
    public List<DmConversationListItem> getInbox(Long userId, String userNo, int size, LocalDateTime cursorAt, Long cursorId) {
        return mapper.selectDmConversations(userId, userNo, size, cursorAt, cursorId);
    }

    @Transactional(readOnly = true)
    public List<DmMessageRow> getMessages(String userId, Long conversationId, int size, Long cursorMessageId) {
        return mapper.selectDmMessages(userId, conversationId, size, cursorMessageId);
    }
}
