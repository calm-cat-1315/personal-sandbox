## Why

个人沙盒需要从零搭起可演进的工程骨架：以 Java/Spring 深耕为主轴、前端不缺席，并按模块化单体承载后续多方向点子。现在落地脚手架，才能用第一个真实功能（日工时导入与展示）验证整条链路。

## What Changes

- 建立 Maven 多模块后端：Spring Boot 4.1 + Java 21 + Spring Modulith 模块化单体
- 建立 Vue 3 + TypeScript + Vite 前端工程，开发期与后端 HTTP 对接
- 引入 `platform` 公共模块与 `work-management` 第一业务模块
- 支持导入一份日工时记录，并在前端展示导入结果
- 提供本地启动与基础 README 说明

## Capabilities

### New Capabilities

- `modular-monolith-foundation`：可运行的模块化单体仓库骨架（后端多模块、前端工程、本地启动与模块边界）
- `work-hours`：日工时记录的导入与前端展示

### Modified Capabilities

- （无——仓库尚无既有规格）

## Impact

- 新建后端 Maven 工程与前端 Vite 工程（当前仓库几乎为空）
- 新增工作管理相关 API、持久化与 Vue 页面
- 引入 Spring Boot、Spring Modulith、JPA、Vue/Vite 等依赖
- 后续点子以新 Modulith 模块 + 前端功能区扩展，不拆微服务
