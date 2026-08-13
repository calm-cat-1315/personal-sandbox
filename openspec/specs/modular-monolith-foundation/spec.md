# modular-monolith-foundation Specification

## Purpose

定义可运行的模块化单体仓库骨架，使后端模块与 Vue 前端能在本地启动，并可通过新增模块持续演进。

## Requirements

### Requirement: 后端模块化单体可运行

系统必须提供可成功启动的 Maven 多模块 Spring Boot 应用（单进程），并暴露供前端调用的 HTTP API。

#### Scenario: 本地启动后端

- **WHEN** 开发者按文档中的本地命令启动后端应用模块
- **THEN** 进程应无致命错误地启动成功
- **AND** HTTP 健康检查或就绪探针端点应返回成功响应

### Requirement: 业务模块按边界隔离

系统必须将后端代码至少组织为共享的 `platform` 区域与 `work-management` 业务模块，使得工作管理行为除装配接线外，不得实现在应用启动模块内部。

#### Scenario: 工作管理位于独立模块

- **WHEN** 开发者检查后端模块布局
- **THEN** 工作管理的领域与 API 代码应位于独立模块中
- **AND** 应用模块的职责应限于启动与组装各模块

### Requirement: 前端应用可运行

系统必须提供 Vue 3 + TypeScript + Vite 前端，可在开发模式下启动，并能调用后端 HTTP API。

#### Scenario: 前端启动并可访问后端

- **WHEN** 在后端已运行的情况下，开发者按文档命令启动前端
- **THEN** 前端开发服务器应可在浏览器中访问
- **AND** 前端应能成功调用至少一个后端 API 端点

### Requirement: 存在本地开发文档

系统必须说明如何安装前置工具，以及如何在本地分别启动后端与前端。

#### Scenario: README 覆盖本地启动

- **WHEN** 开发者打开项目 README
- **THEN** 其中应概括列出所需工具版本
- **AND** 应提供启动后端与前端的命令
