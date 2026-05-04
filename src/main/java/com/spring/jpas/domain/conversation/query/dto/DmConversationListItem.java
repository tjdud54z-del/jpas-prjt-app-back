
package com.spring.jpas.domain.conversation.query.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class DmConversationListItem {
    private Long conversationId;
    private Long peerUserId;
    private String peerUserNo;
    private String peerUserName;
    private Long lastMessageId;
    private LocalDateTime lastMessageAt;
    private String lastMessage;
    private Long lastSenderId;
    private int unreadCount;
}
