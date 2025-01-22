package info.zoio.langchain4jside.extend.langchain.agent;

import dev.langchain4j.service.*;

/**
 * AI助手流式输出接口
 *
 * @author humyna
 * @date 2025/01/22 11:16
 */
public interface AssistantStream {
    @SystemMessage({
            "我的员工编号是{{userId}}",
            "今天是 {{current_date}}."
    })
    @UserMessage("我的问题是: {{userMessage}}。只要告诉我针对问题的回答，字段限制在300字以内，不要返回无关信息。")
    TokenStream streamChat(@MemoryId String memoryId, @V("userId") String userId, @V("userMessage") String userMessage);
}
