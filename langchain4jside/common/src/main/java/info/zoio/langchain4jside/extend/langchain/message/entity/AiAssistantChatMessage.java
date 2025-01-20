package info.zoio.langchain4jside.extend.langchain.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * AI聊天消息对象类
 *
 * @author humyna
 * @date 2025/01/20 14:57
 */
@Data
@Builder
@TableName(value = "ai_assistant_chat_message")
public class AiAssistantChatMessage implements Serializable {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 会话id
     */
    private String sessionId;

    /**
     * 问答追踪id
     */
    private String traceId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务上下文id(格式为【业务类型:用户id】)
     */
    private String businessCxtId;

    /**
     * 用户id，不可为空
     */
    private String userId;

    /**
     * 消息一级类型
     */
    private String messageType;

    /**
     * 消息二级类型
     */
    private String messageSubType;

    /**
     * 消息来源 1:text input 2:voice to text
     */
    private Integer messageSourceType;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 文件地址(音频文件、视频文件、图片、文档等)
     */
    private String fileUrl;

    /**
     * 角色类型(USER_QUESTION\USER\SYSTEM\AI\TOOL_EXECUTION_RESULT)
     */
    private String roleType;

    /**
     * 提示词token大小
     */
    private Integer promptTokens;

    /**
     * 总token大小
     */
    private Integer totalTokens;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     *备注
     */
    private String comments;
}
