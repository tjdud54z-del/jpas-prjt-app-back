package com.spring.jpas.domain.conversation.command.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.time.LocalDateTime;



@Entity
@Table(name = "DM_MESSAGES")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DmMessage {

    /** 메시지 PK */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGE_ID")
    private Long messageId;

    /** 대화방 ID */
    @Column(name = "CONVERSATION_ID", nullable = false)
    private Long conversationId;

    /** 발신자 ID */
    @Column(name = "SENDER_ID", nullable = false)
    private Long senderId;

    /** 메시지 내용 */
    @Column(name = "BODY", nullable = false, length = 2000)
    private String body;

    /** 메시지 타입 (TEXT, IMAGE, SYSTEM 등) */
    @Column(name = "MSG_TYPE", nullable = false, length = 20)
    private String msgType;

    /** 전송 시각 */
    @Column(name = "SENT_AT", nullable = false, updatable = false)
    private LocalDateTime sentAt;

    /** 삭제 시각 (Soft Delete) */
    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    /* ===========================
       JPA Lifecycle
     =========================== */
    @PrePersist
    protected void onCreate() {
        this.sentAt = LocalDateTime.now();
    }

    /* ===========================
       생성자 (등록용)
     =========================== */
    private DmMessage(
            Long conversationId,
            Long senderId,
            String body,
            String msgType
    ) {
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.body = body;
        this.msgType = msgType;
    }

    /* ===========================
       팩토리 메서드
     =========================== */
    public static DmMessage create(Long conversationId, Long senderId, String body, String msgType) {
        DmMessage m = new DmMessage();
        m.conversationId = conversationId;
        m.senderId = senderId;
        m.body = body;
        m.msgType = msgType;
        m.sentAt = LocalDateTime.now();
        return m;
    }

    /* ===========================
       비즈니스 메서드
     =========================== */

    /** 메시지 삭제 (Soft Delete) */
    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    /** 삭제 여부 */
    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}

