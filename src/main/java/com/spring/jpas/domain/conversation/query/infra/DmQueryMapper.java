
package com.spring.jpas.domain.conversation.query.infra;

import com.spring.jpas.domain.conversation.query.dto.DmConversationListItem;
import com.spring.jpas.domain.conversation.query.dto.DmMessageRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DmQueryMapper {

    List<DmConversationListItem> selectDmConversations(
            @Param("userId") Long userId,
            @Param("userNo") String userNo,
            @Param("size") int size,
            @Param("cursorAt") LocalDateTime cursorAt,
            @Param("cursorId") Long cursorId
    );

    List<DmMessageRow> selectDmMessages(
            @Param("userId") Long userId,
            @Param("userNo") String userNo,
            @Param("conversationId") Long conversationId,
            @Param("size") int size,
            @Param("cursorMessageId") Long cursorMessageId
    );

    Long selectDmLastReadMessageId(
            @Param("userId") Long userId,
            @Param("conversationId") Long conversationId
    );
}
