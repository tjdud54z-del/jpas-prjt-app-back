package com.spring.jpas.domain.conversation.command.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DmReadEventDto {
    private Long conversationId;
    private Long readerId;          // 누가 읽었는지
    private Long lastReadMessageId; // 어디까지 읽었는지

}
