
package com.spring.jpas.domain.conversation.query.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class DmMessageRow {
    private Long messageId;
    private Long conversationId;
    private Long senderUserId;
    private String senderUserNo;
    private String senderUserNm;
    private String body;
    private LocalDateTime sentAt;
}
