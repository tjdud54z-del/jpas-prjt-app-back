package com.spring.jpas.domain.conversation.command.application;

import com.spring.jpas.domain.conversation.command.domain.DmConversation;
import com.spring.jpas.domain.conversation.command.domain.DmMessage;
import com.spring.jpas.domain.conversation.command.domain.DmParticipant;
import com.spring.jpas.domain.conversation.command.domain.DmParticipantId;
import com.spring.jpas.domain.conversation.command.infra.DmConversationRepository;
import com.spring.jpas.domain.conversation.command.infra.DmMessageRepository;
import com.spring.jpas.domain.conversation.command.infra.DmParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DmCommandService {

    private final DmConversationRepository conversationRepo;
    private final DmParticipantRepository participantRepo;
    private final DmMessageRepository messageRepo;

    /** (C) DM 대화방 생성 or 조회 (pairKey UNIQUE로 중복 방지) */
    @Transactional
    public Long openConversation(Long meEmployeeId, Long peerEmployeeId) {
        long min = Math.min(meEmployeeId, peerEmployeeId);
        long max = Math.max(meEmployeeId, peerEmployeeId);
        String pairKey = min + "_" + max;

        DmConversation conv = conversationRepo.findByPairKey(pairKey)
                .orElseGet(() -> {
                    DmConversation c = new DmConversation();
                    c.setPairKey(pairKey);
                    DmConversation saved = conversationRepo.save(c);

                    participantRepo.save(DmParticipant.of(saved.getConversationId(), meEmployeeId));
                    participantRepo.save(DmParticipant.of(saved.getConversationId(), peerEmployeeId));

                    return saved;
                });

        return conv.getConversationId();
    }

    /** (C) 메시지 저장 + 대화방 last_message 갱신 */
    @Transactional
    public DmMessage sendMessage(Long conversationId, Long senderId, String body, String m) {

        DmMessage message = DmMessage.create(
                conversationId,
                senderId,
                body,
                m
        );

        DmMessage saved = messageRepo.save(message);

        // last_message 업데이트
        DmConversation c = conversationRepo.findById(conversationId).orElseThrow();
        c.setLastMessageId(saved.getMessageId());
        c.setLastMessageAt(saved.getSentAt());

        return saved;
    }

    /** (U) 읽음 처리(업데이트) -> JPA(Command) */
    @Transactional
    public void markRead(Long conversationId, Long employeeId, Long lastReadMessageId) {
        DmParticipantId id = new DmParticipantId(conversationId, employeeId);
        DmParticipant p = participantRepo.findById(id).orElseThrow();
        p.markRead(lastReadMessageId);
    }

}
