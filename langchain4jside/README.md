
# 项目说明
该项目是基于LangChain实现的AI代理。
jdk1.8

# 计划
1. 支持db存储
2. 支持多用户隔离
3. 记忆支持
4. agent特性
5. tools支持
6. 意图识别处理

# 项目分层
```angular2html
common
|----config
|----extend
|--------langchain
|------------agent
service
|----chat
|--------service
|------------impl
api
|----api
```

# 项目说明
系统启动后，会自动创建数据库表，并初始化数据。
h2 数据库访问地址：http://localhost:8080/langchain4jside/h2
root/root
