## ADDED Requirements

### Requirement: 本地可通过 Compose 启动 MySQL

系统必须提供 Docker Compose 配置，用于在本地启动 MySQL 8 开发实例，并将数据库端口暴露到本机以便应用与外部管理工具连接。

#### Scenario: 启动 Compose 中的 MySQL

- **WHEN** 开发者按文档使用 Docker Compose 启动数据库服务
- **THEN** MySQL 应在本机约定端口上可连接
- **AND** 应存在可供应用使用的开发数据库

### Requirement: 默认开发环境使用 MySQL 持久化

默认本地开发配置下，后端必须将业务数据持久化到 MySQL，而不是嵌入式文件数据库。

#### Scenario: 后端在 MySQL 可用时使用其存储

- **WHEN** 本地 MySQL 开发实例已启动且后端按默认配置启动成功
- **THEN** 后端应使用 MySQL 作为默认持久化存储
- **AND** 通过业务写入的数据应可在该 MySQL 实例中被查询到

## MODIFIED Requirements

### Requirement: 后端模块化单体可运行

系统必须提供可成功启动的 Maven 多模块 Spring Boot 应用（单进程），并暴露供前端调用的 HTTP API。在默认本地开发配置下，启动前须有可用的 MySQL 开发实例。

#### Scenario: 本地启动后端

- **WHEN** 本地 MySQL 开发实例已可用，且开发者按文档中的本地命令启动后端应用模块
- **THEN** 进程应无致命错误地启动成功
- **AND** HTTP 健康检查或就绪探针端点应返回成功响应

### Requirement: 存在本地开发文档

系统必须说明如何安装前置工具（含 Docker），如何启动 MySQL 开发实例，以及如何在本地分别启动后端与前端；并提供可供 Navicat 等工具使用的本机连接信息。

#### Scenario: README 覆盖本地启动

- **WHEN** 开发者打开项目 README
- **THEN** 其中应概括列出所需工具版本（含 Docker）
- **AND** 应提供启动 MySQL、后端与前端的命令
- **AND** 应提供本机 MySQL 的主机、端口及账号等连接说明
