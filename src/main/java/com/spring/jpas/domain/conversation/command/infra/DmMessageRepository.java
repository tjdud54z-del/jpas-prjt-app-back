package com.spring.jpas.domain.conversation.command.infra;

import com.spring.jpas.domain.conversation.command.domain.DmMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DmMessageRepository extends JpaRepository<DmMessage, Long> {
    Page<DmMessage> findByConversationIdOrderBySentAtDesc(Long conversationId, Pageable pageable);
}