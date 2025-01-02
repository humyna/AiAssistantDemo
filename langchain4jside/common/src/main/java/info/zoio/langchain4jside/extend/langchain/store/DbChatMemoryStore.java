package info.zoio.langchain4jside.extend.langchain.store;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private final Map<Object, List<ChatMessage>> messagesByMemoryId = new ConcurrentHashMap();

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        return (List)this.messagesByMemoryId.computeIfAbsent(memoryId, (ignored) -> {
            //TODO 从数据库加载消息

            return new ArrayList();
        });
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        log.info("updateMessages===============memoryId:{},messages={}",memoryId,messages);
        this.messagesByMemoryId.put(memoryId, messages);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        this.messagesByMemoryId.remove(memoryId);
    }

}

