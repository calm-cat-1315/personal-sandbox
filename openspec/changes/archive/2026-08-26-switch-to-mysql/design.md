## Context

当前默认使用 H2 文件库（见 `backend/app/src/main/resources/application.yml`）。动机见 `proposal.md`。业务规格 `work-hours` 不变，仅切换底层存储与本地开发方式。

约束：个人学习沙盒；希望可用 Navicat 连 `localhost:3306`；采用本机已安装的 MySQL 8 作为默认方案，不使用 Docker Compose。

## Goals / Non-Goals

**目标：**
- Spring Boot 默认数据源指向本机 MySQL 8（`localhost:3306/sandbox`）
- 文档写清本机 MySQL 前置、启动顺序与 Navicat 连接参数
- 更新 OpenSpec 项目上下文中的数据库描述

**非目标：**
- Docker Compose 或容器化数据库环境
- 生产级高可用、备份、主从
- 从 H2 自动迁移历史数据
- 引入云托管 MySQL
- 改动日工时业务逻辑或 API 契约
- 本阶段强制引入 Flyway（可选，若成本低可顺带；非必须）

## Decisions

### 决策 1：本机 MySQL 8

- 开发者自行安装并维护本机 MySQL 8 服务
- 默认连接 `localhost:3306`，库名 `sandbox`（JDBC `createDatabaseIfNotExist=true` 可在有权限时自动建库）
- 开发凭据：`root` / `123456`（仅本地沙盒，写入 `application.yml` 与 README）

**原因：** 用户本机已有 MySQL，无需额外引入 Docker；与 Navicat/Spring 共用同一端口。  
**备选：** Docker Compose（可重复但增加依赖，本次不采用）；云 RDS（过重）。

### 决策 2：Spring Boot 默认只连 MySQL

- `pom.xml`：移除默认 H2 runtime；加入 MySQL 驱动
- `application.yml`：`jdbc:mysql://localhost:3306/sandbox?...` + `root` / `123456`
- 暂不保留 H2 profile（保持简单）；测试若需要可后续再加 testcontainers/H2

**原因：** 默认路径唯一，避免「以为连了 MySQL 实际还在 H2」。  
**备选：** `dev-mysql` / `dev-h2` 双 profile（更灵活，也更绕）。

### 决策 3：表结构策略

- 短期继续 `ddl-auto: update`（或 `validate` + 手动），与现实体兼容
- Flyway 留作后续变更（本变更不阻塞）

**原因：** 换库为主，降低同时引入迁移工具的复杂度。

### 决策 4：文档与配置同步

- README：本机 MySQL 8 前置、连接参数表、确保服务已启动后再启后端
- `openspec/config.yaml`：将「H2 文件库」改为「本机 MySQL localhost:3306/sandbox」

### 决策 5：开发凭据

- 使用 `root` + 本地弱口令 `123456`，写在 README 与 `application.yml`
- 不提交真实生产密钥；文档标注仅限本地开发

## Risks / Trade-offs

- [本机 MySQL 未启动导致启动失败] → 缓解：README 强调先确认 MySQL 服务运行；错误日志会指向连接拒绝
- [本机 3306 端口冲突] → 缓解：README 说明可改 MySQL 监听端口并同步改 JDBC
- [弱口令误用于非本地] → 缓解：文档标注仅限本地；后续可改环境变量注入
- [H2 旧数据丢失体感] → 缓解：说明需重新导入 `samples/work-hours-sample.csv`

## Migration Plan

1. 确认本机 MySQL 8 已安装且服务已启动  
2. 更新后端依赖与配置后启动应用  
3. 重新导入示例 CSV（如需要）  
4. 回滚：还原 H2 配置与依赖（旧 H2 文件若仍在或可恢复）

## Open Questions

- 无
