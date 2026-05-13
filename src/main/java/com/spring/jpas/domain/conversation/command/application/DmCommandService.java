package com.spring.jpas.domain.conversation.command.application;

import com.spring.jpas.domain.conversation.command.domain.DmConversation;
import com.spring.jpas.domain.conversation.command.domain.DmMessage;
import com.spring.jpas.domain.conversation.command.domain.DmParticipant;
import com.spring.jpas.domain.conversation.command.domain.DmParticipantId;
import com.spring.jpas.domain.conversation.command.dto.DmReadEventDto;
import com.spring.jpas.domain.conversation.command.infra.DmConversationRepository;
import com.spring.jpas.domain.conversation.command.infra.DmMessageRepository;
import com.spring.jpas.domain.conversation.command.infra.DmParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class DmCommandService {

    private final DmConversationRepository conversationRepository;
    private final DmParticipantRepository participantRepository;
    private final DmMessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    /** (C) DM 대화방 생성 or 조회 (pairKey UNIQUE로 중복 방지) */
    @Transactional
    public Long openConversation(Long meEmployeeId, Long peerEmployeeId) {
        long min = Math.min(meEmployeeId, peerEmployeeId);
        long max = Math.max(meEmployeeId, peerEmployeeId);
        String pairKey = min + "_" + max;

        DmConversation conv = conversationRepository.findByPairKey(pairKey)
                .orElseGet(() -> {
                    DmConversation c = new DmConversation();
                    c.setPairKey(pairKey);
                    DmConversation saved = conversationRepository.save(c);

                    participantRepository.save(DmParticipant.of(saved.getId(), meEmployeeId));
                    participantRepository.save(DmParticipant.of(saved.getId(), peerEmployeeId));

                    return saved;
                });

        return conv.getId();
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

        DmMessage saved = messageRepository.save(message);

        // last_message 업데이트
        DmConversation c = conversationRepository.findById(conversationId).orElseThrow();
        c.setLastMessageId(saved.getMessageId());
        c.setLastMessageAt(saved.getSentAt());

        return saved;
    }

    /** 메세지 전송 */
    public DmMessage send(Long senderId, Long receiverId, String body, String msgType) {

        // 1️⃣ pairKey 계산 (MINID_MAXID)
        String pairKey = generatePairKey(senderId, receiverId);

        // 2️⃣ 대화방 조회 or 생성
        DmConversation conversation = conversationRepository
                .findByPairKey(pairKey)
                .orElseGet(() -> conversationRepository.save(
                        DmConversation.create(pairKey)
                ));

        Long conversationId = conversation.getId();

        // 3️⃣ participants 보장 (sender / receiver)
        ensureParticipant(conversationId, senderId);
        ensureParticipant(conversationId, receiverId);

        // 4️⃣ 메시지 저장
        DmMessage message = DmMessage.create(
                conversationId,
                senderId,
                body,
                msgType
        );
        messageRepository.save(message);

        // 5️⃣ 대화방 마지막 메시지 갱신
        conversation.updateLastMessage(message.getMessageId(), message.getSentAt());
        conversationRepository.save(conversation);
        conversationRepository.flush();

        // 6️⃣ 보낸 사람은 바로 읽음 처리
        DmParticipant senderParticipant =
                participantRepository.findById(
                        new DmParticipantId(conversationId, senderId)
                ).orElseThrow();

        senderParticipant.markRead(message.getMessageId());

        return message;
    }

    private void ensureParticipant(Long conversationId, Long userId) {
        DmParticipantId id = new DmParticipantId(conversationId, userId);
        if (!participantRepository.existsById(id)) {
            participantRepository.save(DmParticipant.create(conversationId, userId));
        }
    }

    private String generatePairKey(Long a, Long b) {
        return (a < b) ? a + "_" + b : b + "_" + a;
    }


    /** (U) 읽음 처리(업데이트) -> JPA(Command) */
    @Transactional
    public void markConversationRead(Long conversationId, Long userId) {
        // 참여자 조회
        DmParticipant participant = participantRepository.findById(
                new DmParticipantId(conversationId, userId)
        ).orElseThrow(() ->
                new IllegalStateException("대화방 참여자가 아닙니다.")
        );

        // 최신 메시지 ID 조회
        Long lastMessageId = participantRepository
                .findLastMessageId(conversationId)
                .orElse(0L);

        log.info("읽음 처리 전 lastRead={}", participant.getLastReadMessageId());
        log.info("최신 메시지 ID={}", lastMessageId);

        // 읽음 처리
        participant.markRead(lastMessageId);

        log.info("읽음 처리 후 lastRead={}", participant.getLastReadMessageId());

        // save (JPA 변경 감지)
        // participantRepository.save(participant); ← 없어도 됨
    }


    /** ✅ 읽음 처리 + 실시간 브로드캐스트 */
    @Transactional
    public void markConversationReadAndBroadcast(Long conversationId, Long userId) {

        // 1) 최신 메시지 ID
        Long lastMessageId = messageRepository.findLastMessageId(conversationId);
        if (lastMessageId == null) lastMessageId = 0L;

        // 2) DB: 내 last_read 갱신
        participantRepository.updateLastReadMessageId(
                conversationId,
                userId,
                lastMessageId
        );

        log.info("✅ READ: conversationId={}, userId={}, lastRead={}",conversationId, userId, lastMessageId);

        // 3) ✅ WebSocket으로 상대에게 알림
        messagingTemplate.convertAndSend(
                "/topic/dm/read/" + conversationId,
                new DmReadEventDto(conversationId, userId, lastMessageId)
        );

        log.info("✅ READ EVENT SENT");

    }



}
