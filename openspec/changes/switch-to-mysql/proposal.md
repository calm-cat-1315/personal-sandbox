## Why

脚手架阶段使用嵌入式 H2，便于快速跑通，但与国内常见的 MySQL 开发方式有差距，也不利于用 Navicat 等工具做真实库练习。现在将默认开发库切换为 MySQL 8，并用 Docker Compose 提供可重复的本地数据库环境。

## What Changes

- **BREAKING（本地开发）**：默认数据源从 H2 改为 MySQL；仅启动后端而不起 MySQL 将无法正常工作
- 新增 Docker Compose 定义，本地以容器方式运行 MySQL 8，并映射 `localhost:3306`
- Spring Boot 默认配置改为连接该 MySQL 实例
- 移除（或不作为默认）H2 依赖与 H2 Console
- 更新 `README.md`：Docker、Compose 启停、JDBC/Navicat 连接信息、启动顺序
- 更新 `openspec/config.yaml` 中的技术栈与本地默认描述

## Capabilities

### New Capabilities

- （无）

### Modified Capabilities

- `modular-monolith-foundation`：本地开发默认持久化改为 MySQL；文档须覆盖 Compose 启停与外部工具连接方式；后端在 MySQL 可用时仍须能成功启动

## Impact

- `docker-compose.yml`（新建）
- `backend/app` 依赖与 `application.yml` 数据源
- 根目录 `README.md`、`openspec/config.yaml`
- 既有 H2 文件数据不迁移；换库后需重新导入示例 CSV
- 日工时等业务 API 行为不变，仅底层存储引擎切换
