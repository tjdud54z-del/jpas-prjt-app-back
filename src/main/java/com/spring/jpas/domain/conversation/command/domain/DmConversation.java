package com.spring.jpas.domain.conversation.command.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "jpas_tb_dm_conversations")
@Getter
@Setter
public class DmConversation {


@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONVERSATION_ID")
    private Long id;

    @Column(name = "PAIR_KEY", nullable = false, unique = true)
    private String pairKey;

    @Column(name = "LAST_MESSAGE_ID")
    private Long lastMessageId;

    @Column(name = "LAST_MESSAGE_AT")
    private LocalDateTime lastMessageAt;

    public static DmConversation create(String pairKey) {
        DmConversation c = new DmConversation();
        c.pairKey = pairKey;
        return c;
    }

    public void updateLastMessage(Long messageId, LocalDateTime sentAt) {
        this.lastMessageId = messageId;
        this.lastMessageAt = sentAt;
    }

}

