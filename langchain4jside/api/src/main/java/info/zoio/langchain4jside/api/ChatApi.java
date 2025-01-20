package info.zoio.langchain4jside.api;

import info.zoio.langchain4jside.business.chat.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
