package com.spring.jpas.domain.conversation.command.infra;

import com.spring.jpas.domain.conversation.command.domain.DmParticipant;
import com.spring.jpas.domain.conversation.command.domain.DmParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DmParticipantRepository extends JpaRepository<DmParticipant, DmParticipantId> {
//    List<DmParticipant> findByIdEmployeeId(Long employeeId);
//    List<DmParticipant> findByIdConversationId(Long conversation);

    /** 대화방에서 가장 최신 메시지 ID */
    @Query("""
        select max(m.id)
        from DmMessage m
        where m.conversationId = :conversationId
          and m.deletedAt is null
    """)
    Optional<Long> findLastMessageId(Long conversationId);

}
