package info.zoio.langchain4jside.business.chat.service.impl;

import dev.langchain4j.data.message.ChatMessageType;
import info.zoio.langchain4jside.business.chat.service.ChatService;
import info.zoio.langchain4jside.extend.langchain.agent.AiBusinessTypeEnum;
import info.zoio.langchain4jside.extend.langchain.agent.AssistantAgent;
import info.zoio.langchain4jside.extend.langchain.store.DbChatMemoryStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
        String answer;
        String businessCxtId = userId;
        long start = System.currentTimeMillis();
        dbChatMemoryStore.saveMessagesToDb(businessCxtId, null , userId, 1, null, null, message, null, ChatMessageType.USER);
        long comIntendStart = System.currentTimeMillis();
        AiBusinessTypeEnum businessTypeEnum = agent.commonIntentIdentify(businessCxtId, userId, message);
        log.info("chat commonIntentIdentify use = {} ms, userId = {}", System.currentTimeMillis() - comIntendStart, userId);
        if (Objects.isNull(businessTypeEnum)) {
            log.warn("commonIntentIdentify error, user message={}", message);
            return "无法识别用户的意图";
        }

        businessCxtId =  businessTypeEnum.name().concat(":").concat(businessCxtId);
        switch (businessTypeEnum) {
            //用户相关
            case USER:
                long userStart = System.currentTimeMillis();
                //demo
                answer = "user query";
                log.info("chat user use = {} ms, saleNo = {}", System.currentTimeMillis() - userStart, userId);
                break;
            //用户相关
            case PRODUCT:
                long productStart = System.currentTimeMillis();
                //demo
                answer = "product query";
                log.info("chat user use = {} ms, userId = {}", System.currentTimeMillis() - productStart, userId);
                break;
            case OTHER:
                long otherStart = System.currentTimeMillis();
                answer = agent.chat(businessCxtId, userId, message);
                log.info("chat other use = {} ms, userId = {}", System.currentTimeMillis() - otherStart, userId);
                break;
            default:
                //demo
                answer = "暂不支持的场景";
                log.warn("暂不支持的业务场景，user message={}", message);
                break;
        }
        log.info("chat end.use = {} ms,params: userId={},message={}",System.currentTimeMillis() - start,userId,message);
        return answer;
    }
}
