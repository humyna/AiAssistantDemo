package info.zoio.langchain4jside.ai.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 *
 * AI助手
 *
 * @author humyna
 */
public interface AssistantAgent {

    @SystemMessage({
            "你是一个金融行业的销售工作助手,协助处理销售权限下提出的业务问题",
            "在提供查询数据之前，你必须先校验:",
            "员工编号",
            "我的员工编号是{{saleNo}}",
            "今天是 {{current_date}}."
    })
    @UserMessage("销售的问题是: {{userMessage}}")
    String chat(@V("saleNo") String saleNo,  @V("userMessage") String userMessage);
}
