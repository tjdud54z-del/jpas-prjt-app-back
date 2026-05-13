package com.spring.jpas.domain.conversation.command.infra;

import com.spring.jpas.domain.conversation.command.domain.DmMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DmMessageRepository extends JpaRepository<DmMessage, Long> {
    Page<DmMessage> findByConversationIdOrderBySentAtDesc(Long conversationId, Pageable pageable);


    @Query("""
        select max(m.messageId)
        from DmMessage m
        where m.conversationId = :conversationId
          and m.deletedAt is null
    """)
    Long findLastMessageId(@Param("conversationId") Long conversationId);


}