-- 创建后端管理系统数据库
CREATE DATABASE IF NOT EXISTS backend_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE backend_management;

CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID，主键自增',
                       username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名，唯一',
                       password VARCHAR(255) NOT NULL COMMENT '加密后的密码',
                       real_name VARCHAR(50) COMMENT '真实姓名',
                       email VARCHAR(100) UNIQUE COMMENT '电子邮箱，唯一',
                       phone VARCHAR(20) COMMENT '手机号码',
                       user_type VARCHAR(20) COMMENT '用户类型',
                       is_super_admin TINYINT DEFAULT 0 COMMENT '是否超级管理员：0-否, 1-是',
                       status TINYINT DEFAULT 1 COMMENT '账号状态：0-禁用, 1-启用',
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                       is_deleted TINYINT DEFAULT 0 COMMENT '删除标志：0-未删除, 1-已删除',
                       INDEX idx_is_deleted (is_deleted) COMMENT '删除状态索引'
) COMMENT '系统用户表';


CREATE TABLE roles (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID，主键自增',
                       name VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称，唯一',
                       description VARCHAR(255) COMMENT '角色描述',
                       status TINYINT DEFAULT 1 COMMENT '状态：0-禁用, 1-启用',
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                       is_deleted TINYINT DEFAULT 0 COMMENT '删除标志：0-未删除, 1-已删除',
                       INDEX idx_is_deleted (is_deleted) COMMENT '删除状态索引'
) COMMENT '角色表';

CREATE TABLE permissions (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '权限ID，主键自增',
                             name VARCHAR(50) NOT NULL UNIQUE COMMENT '权限名称，唯一',
                             code VARCHAR(50) NOT NULL UNIQUE COMMENT '权限标识符，用于权限验证，唯一',
                             description VARCHAR(255) COMMENT '权限描述',
                             type TINYINT COMMENT '权限类型：1-菜单, 2-按钮, 3-API',
                             parent_id BIGINT DEFAULT 0 COMMENT '父权限ID，0表示一级权限',
                             path VARCHAR(255) COMMENT '菜单访问路径',
                             status TINYINT DEFAULT 1 COMMENT '状态：0-禁用, 1-启用',
                             created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             is_deleted TINYINT DEFAULT 0 COMMENT '删除标志：0-未删除, 1-已删除',
                             INDEX idx_is_deleted (is_deleted) COMMENT '删除状态索引'
) COMMENT '权限表';

CREATE TABLE user_roles (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID，主键自增',
                            user_id BIGINT NOT NULL COMMENT '用户ID',
                            role_id BIGINT NOT NULL COMMENT '角色ID',
                            created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            UNIQUE KEY (user_id, role_id) COMMENT '用户ID和角色ID组合唯一',
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
) COMMENT '用户角色关联表';

CREATE TABLE role_permissions (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID，主键自增',
                                  role_id BIGINT NOT NULL COMMENT '角色ID',
                                  permission_id BIGINT NOT NULL COMMENT '权限ID',
                                  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  UNIQUE KEY (role_id, permission_id) COMMENT '角色ID和权限ID组合唯一',
                                  FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
                                  FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
) COMMENT '角色权限关联表';

CREATE TABLE operation_logs (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID，主键自增',
                                user_id BIGINT COMMENT '操作用户ID',
                                module VARCHAR(50) COMMENT '操作模块名称',
                                operation VARCHAR(50) COMMENT '操作类型，如新增、删除、修改等',
                                method VARCHAR(10) COMMENT 'HTTP请求方法，如GET、POST等',
                                url VARCHAR(255) COMMENT '请求URL',
                                params TEXT COMMENT '请求参数，JSON格式',
                                status TINYINT COMMENT '操作状态：0-失败, 1-成功',
                                error_msg TEXT COMMENT '错误信息，操作失败时记录',
                                created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间'
) COMMENT '操作日志表';

CREATE TABLE login_logs (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID，主键自增',
                            user_id BIGINT NOT NULL COMMENT '登录用户ID',
                            username VARCHAR(50) NOT NULL COMMENT '登录用户名',
                            ip VARCHAR(50) COMMENT '登录IP地址',
                            browser VARCHAR(100) COMMENT '浏览器类型',
                            os VARCHAR(50) COMMENT '操作系统',
                            status TINYINT COMMENT '登录状态：0-失败, 1-成功',
                            msg VARCHAR(255) COMMENT '提示消息',
                            created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间'
) COMMENT '登录日志表';

-- users 表（用户列表查询最常用场景：状态筛选 + 时间倒序 + 姓名模糊）
CREATE INDEX idx_users_status_type_created ON users(status, user_type, created_at DESC);
CREATE INDEX idx_users_realname ON users(real_name);           -- 姓名模糊查询
CREATE INDEX idx_users_username ON users(username);           -- 登录精确查（非常重要）

-- roles 表（角色列表基本都是查启用 + 时间倒序）
CREATE INDEX idx_roles_status_created ON roles(status, created_at DESC);

-- permissions 表（菜单树查询 + 权限校验最核心！）
CREATE INDEX idx_perm_parent_status ON permissions(parent_id, status, type);  -- 树形查询 + 状态过滤
CREATE INDEX idx_perm_code ON permissions(code);               -- Sa-Token 权限校验必走这个！

-- operation_logs 操作日志（按用户 + 时间范围查询最常见）
CREATE INDEX idx_oplog_user_time ON operation_logs(user_id, created_at DESC);
CREATE INDEX idx_oplog_time ON operation_logs(created_at DESC);  -- 时间范围查询

-- login_logs 登录日志（最常用查询方式）
CREATE INDEX idx_login_user_time ON login_logs(user_id, created_at DESC);
CREATE INDEX idx_login_time ON login_logs(created_at DESC);
CREATE INDEX idx_login_ip ON login_logs(ip);                   -- 封IP功能专用
CREATE INDEX idx_login_username ON login_logs(username);       -- 按用户名查登录记录

-- 初始化数据，添加超级管理员账号
-- 账号密码：admin/admin123 数据库使用了sa-token加密
INSERT INTO users (username,password,real_name,email,phone,user_type,is_super_admin,status,created_at,updated_at,is_deleted) VALUES ('admin','$2a$10$AUCqT87wwMqdStkX4snDcuIR5v6VAEanj7123dFNrxBG8uBu7UXsW', '超级管理员','admin@yourcompany.com','13800138000','admin',1,1,NOW(),NOW(),0);

INSERT INTO roles (name,description,status,created_at,updated_at,is_deleted) VALUES ('超级管理员','拥有系统全部权限的最高管理员',1,NOW(),NOW(),0);

INSERT INTO user_roles (user_id,role_id,created_at) VALUES (1,1,NOW());
