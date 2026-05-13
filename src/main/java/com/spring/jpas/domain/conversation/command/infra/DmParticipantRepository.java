package com.spring.jpas.domain.conversation.command.infra;

import com.spring.jpas.domain.conversation.command.domain.DmParticipant;
import com.spring.jpas.domain.conversation.command.domain.DmParticipantId;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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


    @Modifying(clearAutomatically = true)
    @Query("""
        update DmParticipant p
           set p.lastReadMessageId = :lastReadMessageId
         where p.id.conversationId = :conversationId
           and p.id.userId = :userId
    """)
    int updateLastReadMessageId(
        @Param("conversationId") Long conversationId,
        @Param("userId") Long userId,
        @Param("lastReadMessageId") Long lastReadMessageId
    );


}
