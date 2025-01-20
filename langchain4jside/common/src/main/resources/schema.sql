CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS ai_assistant_chat_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键id',
    session_id VARCHAR(32) COMMENT '会话id',
    trace_id VARCHAR(64) COMMENT '问答追踪id',
    business_type VARCHAR(64) COMMENT '业务类型',
    business_cxt_id VARCHAR(128) COMMENT '业务上下文id(格式为【业务类型:用户id】)',
    user_id VARCHAR(64) NOT NULL COMMENT '用户id，不可为空',
    message_type VARCHAR(64) COMMENT '消息一级类型',
    message_sub_type VARCHAR(255) COMMENT '消息二级类型',
    message_source_type INT COMMENT '消息来源 1:text input 2:voice to text',
    message_content LONGTEXT COMMENT '消息内容',
    file_url VARCHAR(255) COMMENT '文件地址(音频文件、视频文件、图片、文档等)',
    role_type VARCHAR(32) COMMENT '角色类型(USER_QUESTION\USER\SYSTEM\AI\TOOL_EXECUTION_RESULT)',
    prompt_tokens INT COMMENT '提示词token大小',
    total_tokens INT COMMENT '总token大小',
    create_time TIMESTAMP COMMENT '创建时间',
    comments VARCHAR(128) COMMENT '备注'
);