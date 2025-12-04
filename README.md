# BackendManagement 极简后台管理系统

一个 **极简、高性能、生产级** 的 Spring Boot + MyBatis-Plus + Sa-Token 后台管理系统框架

## 技术栈
- Spring Boot 3.1.6 + MyBatis-Plus 3.5.8 + Sa-Token 1.40.0
- MySQL 8 + Druid + Hutool + Lombok
- 内置 MyBatis-Plus 代码生成器（支持自定义模板）
- 标准 RBAC 权限模型（用户→角色→权限）
- 登录/操作双日志 + 登录IP归属地
- 全部索引优化 + 生产级配置

## API文档
- Swagger UI：http://localhost:8888/api/swagger-ui/index.html

## 项目结构

```bash
backend-management/
├── sql/init.sql                     -- 初始化数据（超级管理员）
├── src/main/java/com/kt/project
│   ├── controller                   -- 控制器
│   ├── service                      -- 业务逻辑
│   ├── entity                       -- 实体类
│   ├── mapper                       -- MyBatis Mapper
│   ├── generator                    -- 代码生成器（一键生成CRUD）
│   ├── aspect                       -- AOP日志
│   └── config                       -- Sa-Token配置
├── src/main/resources
│   ├── application.yml             -- 主配置文件
│   ├── application-dev.yml         -- 开发环境配置
│   ├── logback-spring.xml           -- 日志配置（控制台+文件）
│   ├── mapper                       -- MyBatis XML（可选）
│   └── static/templates             -- 静态资源
├── mvnw / mvnw.cmd                  -- 一键启动（无需安装Maven）
└── docker-compose.yml               -- 一键部署（可选）

