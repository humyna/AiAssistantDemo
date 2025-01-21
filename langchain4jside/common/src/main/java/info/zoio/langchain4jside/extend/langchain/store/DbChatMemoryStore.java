package info.zoio.langchain4jside.extend.langchain.store;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageType;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import info.zoio.langchain4jside.extend.langchain.message.entity.AiAssistantChatMessage;
import info.zoio.langchain4jside.extend.langchain.message.service.AiAssistantChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 实现 DBChatMemoryStore 替换 InMemoryChatMemoryStore
 *
 * @author humyna
 * @date 2025/01/02 20:45
 */
@Component
@Slf4j
public class DbChatMemoryStore implements ChatMemoryStore {
    //内存实现仅支持单节点部署；多节点部署需要切换到redis等持久化方案
    private final Map<Object, List<ChatMessage>> messagesByMemoryId = new ConcurrentHashMap();
    @Autowired
    AiAssistantChatMessageService aiAssistantChatMessageService;

    @Override
    public List<ChatMessage> getMessages(Object businessCxtId) {
        List<ChatMessage> messages = (List) this.messagesByMemoryId.get(businessCxtId);
        if (messages == null) {
            return Collections.emptyList();
        }
        return messages;
    }

    @Override
    public void updateMessages(Object businessCxtId, List<ChatMessage> messages) {
        log.info("updateMessages===============businessCxtId:{},messages={}", businessCxtId, messages);
        String businessType = null;
        String userId = businessCxtId.toString();
        if(businessCxtId.toString().contains(":")) {
            int lastIndex = businessCxtId.toString().lastIndexOf(":");
            userId = businessCxtId.toString().substring(lastIndex + 1);
            businessType = businessCxtId.toString().substring(0, lastIndex);
        }

        this.messagesByMemoryId.put(businessCxtId, messages);

        ChatMessage lastChatMessage = messages.get(messages.size() - 1);
//        if (lastChatMessage.type() != ChatMessageType.USER) {
            //记录非用户角色的上下文信息
            saveMessagesToDb(
                    businessCxtId, businessType, userId, 0,
                    null, null, lastChatMessage.text(), null,
                    lastChatMessage.type()
            );
//        }
    }

    @Override
    public void deleteMessages(Object businessCxtId) {
        this.messagesByMemoryId.remove(businessCxtId);
    }

    public void saveMessagesToDb(Object businessCxtId, String businessType,String userId,
                                 Integer messageSourceType, String messageType, String messageSubType, String messageContent, String fileUrl
                                , ChatMessageType roleType) {
        try {
            String roleTypeName = roleType.name();
            if (messageSourceType != 0) {
                //用户与AI会话的初始语料
                roleTypeName = roleTypeName.concat("_QUESTION");
            }
            //记录上下文信息
            AiAssistantChatMessage aiAssistantChatMessage = AiAssistantChatMessage.builder()
                    .businessCxtId(businessCxtId.toString())
                    .businessType(businessType)
                    .userId(userId)
                    .messageSourceType(messageSourceType)
                    .messageType(messageType)
                    .messageSubType(messageSubType)
                    .messageContent(messageContent)
                    .fileUrl(fileUrl)
                    .roleType(roleTypeName)
                    .createTime(new Date())
                    .build();
            if (!aiAssistantChatMessageService.save(aiAssistantChatMessage)) {
                throw new RuntimeException("会话上下文存储失败");
            }
        } catch (RuntimeException e) {
            log.error("会话上下文存储DB失败", e);
        }
    }
}