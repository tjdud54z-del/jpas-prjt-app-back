package com.spring.jpas.domain.conversation.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    private Long senderId;
    private Long receiverId;   // 상대방 유저 ID
    private String body;       // 메시지 내용
    private String msgType;    // TEXT / IMAGE ...


    private Long readerId;          // 누가 읽었는지
    private Long lastReadMessageId; // 어디까지 읽었는지


}
