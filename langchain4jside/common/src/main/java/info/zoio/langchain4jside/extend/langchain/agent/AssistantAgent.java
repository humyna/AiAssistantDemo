package info.zoio.langchain4jside.extend.langchain.agent;

import dev.langchain4j.service.MemoryId;
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
            "我的员工编号是{{userId}}",
            "今天是 {{current_date}}."
    })
    @UserMessage("销售的问题是: {{userMessage}}")
    String chat(@MemoryId String memoryId, @V("userId") String userId, @V("userMessage") String userMessage);

    @SystemMessage({
            "你是一个意图识别助手,根据我的问题提取意图标签，意图标签只能是下面3种：[1.用户; 2.产品; 3.其他]",
            "你的答案中只能返回意图标签对应的英文描述，'用户'等于'USER','产品'等于'PRODUCT',其他'等于'OTHER'",
            "比如我问'用户张三的注册信息'，你只要返回'USER'，不能包含其他信息。",
            "下面是补充信息",
            "如果问题中包含以下任意一个关键字：'用户'、'客户'，则返回 'USER';",
            "问题中只要包含关键字'产品'，返回'PRODUCT';",
            "对于无法识别的意图默认其他，返回'OTHER';",
            "我的员工编号是{{userId}}",
            "今天是 {{current_date}}."
    })
    @UserMessage("我的问题是: {{userMessage}}")
    AiBusinessTypeEnum commonIntentIdentify(@MemoryId String memoryId, @V("userId") String userId, @V("userMessage") String userMessage);
}
