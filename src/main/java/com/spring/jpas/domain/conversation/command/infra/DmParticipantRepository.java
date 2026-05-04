package com.spring.jpas.domain.conversation.command.infra;

import com.spring.jpas.domain.conversation.command.domain.DmParticipant;
import com.spring.jpas.domain.conversation.command.domain.DmParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DmParticipantRepository extends JpaRepository<DmParticipant, DmParticipantId> {
//    List<DmParticipant> findByIdEmployeeId(Long employeeId);
//    List<DmParticipant> findByIdConversationId(Long conversation);
}
