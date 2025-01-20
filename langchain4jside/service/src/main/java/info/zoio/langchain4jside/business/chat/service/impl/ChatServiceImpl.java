package info.zoio.langchain4jside.business.chat.service.impl;

import dev.langchain4j.data.message.ChatMessageType;
import info.zoio.langchain4jside.business.chat.service.ChatService;
import info.zoio.langchain4jside.extend.langchain.agent.AssistantAgent;
import info.zoio.langchain4jside.extend.langchain.store.DbChatMemoryStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AI聊天实现类
 *
 * @author humyna
 * @date 2025/01/20 15:36
 */
@Service
@Slf4j
public class ChatServiceImpl implements ChatService {
    @Autowired
    AssistantAgent agent;
    @Autowired
    DbChatMemoryStore dbChatMemoryStore;

    @Override
    public String chat(String userId, String message) {
        log.info("chat start,userId={},message={}",userId,message);
        //demo
        String businessType = "sale";
        String businessCxtId = businessType + ":" + userId;
        dbChatMemoryStore.saveMessagesToDb(businessCxtId, businessType, userId, 1, null, null, message, null, ChatMessageType.USER);
        long start = System.currentTimeMillis();
        String answer = agent.chat(userId, userId, message);
        log.info("chat end. agent.chat use = {} ms,params: userId={},message={}",System.currentTimeMillis() - start,userId,message);
        return answer;
    }
}
