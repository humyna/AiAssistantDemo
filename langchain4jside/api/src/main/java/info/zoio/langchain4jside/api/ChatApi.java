package info.zoio.langchain4jside.api;

import info.zoio.langchain4jside.ai.agent.AssistantAgent;
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
    AssistantAgent agent;

    @GetMapping("/demo")
    public String demo(){
        return "hello ai";
    }

    @PostMapping(value="/chat", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String chat(@RequestParam("message") String message,
                                 @RequestParam("saleNo") String saleNo){
        try {
            long start = System.currentTimeMillis();
            String answer = agent.chat(saleNo,message);
            log.info("/chat use = {} ms,params: saleNo={},message={}",System.currentTimeMillis() - start,saleNo,message);
            return  answer;
        } catch (Exception e) {
            log.error("/chat error,params: saleNo={},message={}",saleNo,message, e);
            return  "处理失败";
        }
    }
}
