
package com.spring.jpas.domain.conversation.command.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "jpas_tb_dm_participants")
@Getter
@NoArgsConstructor
public class DmParticipant {

    @EmbeddedId
    private DmParticipantId id;

    /** 참여 시각 */
    @Column(name = "JOINED_AT", nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    /** 마지막 읽은 메시지 ID */
    @Column(name = "LAST_READ_MESSAGE_ID")
    private Long lastReadMessageId;

    /** 알림 음소거 여부 (Y/N) */
    @Column(name = "MUTED_YN", nullable = false, length = 1)
    private String mutedYn;

    /* ===========================
       JPA Lifecycle
     =========================== */
    @PrePersist
    protected void onCreate() {
        if (this.joinedAt == null) this.joinedAt = LocalDateTime.now();
        if (this.mutedYn == null) this.mutedYn = "N";
    }

    /* ===========================
       정적 팩토리 (등록)
     =========================== */
    public static DmParticipant of(Long conversationId, Long userId) {
        DmParticipant p = new DmParticipant();
        p.id = new DmParticipantId(conversationId, userId);
        p.joinedAt = LocalDateTime.now();
        p.mutedYn = "N";
        return p;
    }


    public static DmParticipant create(Long conversationId, Long userId) {
        DmParticipant p = new DmParticipant();
        p.id = new DmParticipantId(conversationId, userId);
        p.lastReadMessageId = 0L;
        return p;
    }


    /* ===========================
       도메인 행위
     =========================== */

    /** 읽음 처리 */
    public void markRead(Long messageId) {
        if (lastReadMessageId == null || messageId > lastReadMessageId) {
            this.lastReadMessageId = messageId;
        }
    }


    /** 음소거 */
    public void mute() {
        this.mutedYn = "Y";
    }

    /** 음소거 해제 */
    public void unmute() {
        this.mutedYn = "N";
    }
}
