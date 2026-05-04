package com.spring.jpas.domain.conversation.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class DmDto {
    private Long peerEmployeeId;
    private String senderUserId;    // ✅ 로그인 ID
    private String receiverUserId;  // ✅ 로그인 ID
    private Long conversationId;
    private Long messageId;
    private Long senderEmployeeId;
    private Long receiverEmployeeId;
    private LocalDateTime sentAt;
    private String content;
}
