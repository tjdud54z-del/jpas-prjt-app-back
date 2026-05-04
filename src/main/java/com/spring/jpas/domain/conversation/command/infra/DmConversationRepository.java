package com.spring.jpas.domain.conversation.command.infra;

import com.spring.jpas.domain.conversation.command.domain.DmConversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DmConversationRepository extends JpaRepository<DmConversation, Long> {
    Optional<DmConversation> findByPairKey(String pairKey);
}
