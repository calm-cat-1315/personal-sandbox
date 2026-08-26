## Why

脚手架阶段使用嵌入式 H2，便于快速跑通，但与国内常见的 MySQL 开发方式有差距，也不利于用 Navicat 等工具做真实库练习。现在将默认开发库切换为 MySQL 8，直连本机已安装的 MySQL 实例。

## What Changes

- **BREAKING（本地开发）**：默认数据源从 H2 改为 MySQL；仅启动后端而本机 MySQL 未运行将无法正常工作
- Spring Boot 默认配置改为连接本机 `localhost:3306` 上的 MySQL 实例
- 移除（或不作为默认）H2 依赖与 H2 Console
- 更新 `README.md`：本机 MySQL 前置、连接参数、启动顺序、Navicat/JDBC 说明
- 更新 `openspec/config.yaml` 中的技术栈与本地默认描述

## Capabilities

### New Capabilities

- （无）

### Modified Capabilities

- `modular-monolith-foundation`：本地开发默认持久化改为 MySQL；文档须覆盖本机 MySQL 连接方式与外部工具连接参数；后端在 MySQL 可用时仍须能成功启动

## Impact

- `backend/app` 依赖与 `application.yml` 数据源
- 根目录 `README.md`、`openspec/config.yaml`
- 既有 H2 文件数据不迁移；换库后需重新导入示例 CSV
- 日工时等业务 API 行为不变，仅底层存储引擎切换
