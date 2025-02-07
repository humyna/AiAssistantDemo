package info.zoio.langchain4jside.business.chat.service.impl;

import dev.langchain4j.data.message.ChatMessageType;
import dev.langchain4j.service.TokenStream;
import info.zoio.langchain4jside.business.chat.service.ChatService;
import info.zoio.langchain4jside.extend.langchain.enums.AiBusinessTypeEnum;
import info.zoio.langchain4jside.extend.langchain.agent.AssistantAgent;
import info.zoio.langchain4jside.extend.langchain.agent.AssistantStream;
import info.zoio.langchain4jside.extend.langchain.sse.SSEUtils;
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
    AssistantStream assistantStream;
    @Autowired
    DbChatMemoryStore dbChatMemoryStore;

    @Override
    public String chatDemo(String userId, String message) {
        return agent.chat(userId, userId, message);
    }

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
                log.info("chat user use = {} ms, userId = {}", System.currentTimeMillis() - userStart, userId);
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

    @Override
    public void chatStream(String userId, String message) {
        try {
            // 使用qwen模型报错：Model 'qwen-qwen' is unknown to jtokkit ;  langchain4j的bug，需要升级jar至0.32.0(jdk17 springboot3.2+)
            // 使用gpt-4-1106-preview模型没问题
            TokenStream tokenStream = assistantStream.streamChat(userId, userId, message);
            tokenStream.onNext(token ->
                    {
                        try {
                            log.info("chatStream tokenStream token={}", token);
                            SSEUtils.pubMsg(userId, userId, userId, token);
                        } catch (Exception e) {
                            log.error("chatStream sseEmitter.send error", e);
                        }
                    }
            )
                    .onComplete(response -> {
                        //there is a bug in langchain4j: TokenStream.onComplete can not get useage. see:https://github.com/langchain4j/langchain4j/issues/1390
                        // the bug makes request can not be closed
                        log.info("chatStream tokenStream completed" + response.tokenUsage());
                        SSEUtils.closeSub(userId);
                    })
                    .onError(error -> {
                        log.error("chatStream tokenStream err", error);
                        SSEUtils.closeSub(userId);
                    })
                    .start();
        } catch (Exception e) {
            log.error("chatStream error", e);
            SSEUtils.closeSub(userId);
        }
    }
}
