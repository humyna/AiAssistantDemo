package info.zoio.langchain4jside.extend.langchain.message.service;

import com.github.yulichang.base.MPJBaseServiceImpl;
import info.zoio.langchain4jside.extend.langchain.message.entity.AiAssistantChatMessage;
import info.zoio.langchain4jside.extend.langchain.message.mapper.AiAssistantChatMessageMapper;
import org.springframework.stereotype.Service;

/**
 * AI聊天消息实现类
 *
 * @author humyna
 * @date 2025/01/16 17:15
 */
@Service
public class AiAssistantChatMessageService extends MPJBaseServiceImpl<AiAssistantChatMessageMapper, AiAssistantChatMessage> {
}
