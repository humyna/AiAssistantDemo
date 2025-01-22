package info.zoio.langchain4jside.business.chat.service;

/**
 * AI聊天接口类
 *
 * @author humyna
 * @date 2025/01/20 15:33
 */
public interface ChatService {
    /**
    * 阻塞式输出聊天接口
     *
    * @author humyna
    * @date 2025/1/22 11:20
     * @param userId
     * @param message
     * @return java.lang.String
    */
    String chat(String userId, String message);

    /**
    * 流式输出聊天接口
    * @author humyna
    * @date 2025/1/22 11:20
     * @param userId
     * @param message
    */
    void chatStream(String userId, String message);
}
