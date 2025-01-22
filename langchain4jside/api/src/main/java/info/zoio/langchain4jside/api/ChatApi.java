package info.zoio.langchain4jside.api;

import info.zoio.langchain4jside.business.chat.service.ChatService;
import info.zoio.langchain4jside.extend.langchain.sse.SSEUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author humyna
 * @date 2024/11/29 14:19
 */
@Slf4j
@RestController
@RequestMapping("/ai")
public class ChatApi {
    @Autowired
    ChatService chatService;

    @GetMapping("/demo")
    public String demo(){
        return "hello ai";
    }

    @PostMapping(value="/chat", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String chat(@RequestParam("message") String message,
                                 @RequestParam("userId") String userId){
        try {
            long start = System.currentTimeMillis();
            String answer = chatService.chat(userId,message);
            log.info("/chat use = {} ms,params: userId={},message={}",System.currentTimeMillis() - start,userId,message);
            return  answer;
        } catch (Exception e) {
            log.error("/chat error,params: userId={},message={}",userId,message, e);
            return  "处理失败";
        }
    }

    @GetMapping(value = "/chatStream", produces = "text/event-stream;charset=UTF-8")
    public SseEmitter chatStream(
            @RequestParam("message") String message,
            @RequestParam("userId") String userId,
            @RequestParam("secKey") String secKey) {

        SseEmitter emitter = null;
        try {
            long start = System.currentTimeMillis();
            if (!"xxmmllqq".equals(secKey)) {
                return  SSEUtils.failNotice("无访问权限");
            }
            emitter = SSEUtils.addSub(userId);
            chatService.chatStream(userId, message);
            log.info("/chatStream use = {} ms", System.currentTimeMillis() - start);
            return emitter;
        } catch (Exception e) {
            log.error("/chatStream error",e);
            if(emitter !=null){
                SSEUtils.closeSub(userId);
            }
            return SSEUtils.failNotice("error");
        }
    }
}
