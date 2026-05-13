package com.spring.jpas.domain.conversation.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DmParticipantId implements Serializable {

    @Column(name = "CONVERSATION_ID", nullable = false)
    private Long conversationId;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;
}