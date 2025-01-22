package info.zoio.langchain4jside.extend.langchain.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SseEmitter工具类
 *
 * @author humyna
 * @date 2025/01/22 11:24
 */
@Slf4j
public class SSEUtils {
    // 默认超时时间60秒
    private static Long DEFAULT_TIME_OUT = 1*60*1000L;

    private static final Map<String, SseEmitter> subscribeMap = new ConcurrentHashMap<>();

    /** 添加订阅 */
    public static SseEmitter addSub(String subId) {
        if (null == subId || "".equals(subId)) {
            return null;
        }

        SseEmitter emitter = subscribeMap.get(subId);
        if (null == emitter) {
            emitter = new SseEmitter(DEFAULT_TIME_OUT);

            emitter.onTimeout(() -> {
                // 注册超时回调，超时后触发
                log.info("onTimeout,subId={}" , subId);
                closeSub(subId);
            });

            emitter.onCompletion(() -> {
                // 注册完成回调，调用 emitter.complete() 触发
                log.info("onCompletion,subId={}" , subId);
                closeSub(subId);
            });
            subscribeMap.put(subId, emitter);
        }
        return emitter;
    }

    public static void pubMsg(String subId, String name, String id, Object msg) {
        SseEmitter emitter = subscribeMap.get(subId);
        if (null != emitter) {
            try {
                log.debug("emitter send msg:{}",msg);
                emitter.send(SseEmitter.event().name(name).id(id).data(msg));
            } catch (Exception e) {
                log.error("emitter send error,msg:{}",msg);
            }
        }
    }

    // 关闭订阅
    public static void closeSub(String subId) {
        SseEmitter emitter = subscribeMap.get(subId);
        if (null != emitter) {
            try {
                emitter.complete();
            } catch (Exception e) {
                log.error("closeSub error",e);
            } finally {
                subscribeMap.remove(subId);
            }
        }
    }

    public static SseEmitter failNotice(String  msg){
        SseEmitter emitter = new SseEmitter(DEFAULT_TIME_OUT);
        try {
            emitter.send(msg);
        } catch (IOException e) {
            log.error("emitter msg error,msg:{}",msg);
        }finally {
            emitter.complete();
        }
        return emitter;
    }
}
