## Context

当前默认使用 H2 文件库（见 `backend/app/src/main/resources/application.yml`）。动机见 `proposal.md`。业务规格 `work-hours` 不变，仅切换底层存储与本地开发方式。

约束：个人学习沙盒；希望可用 Navicat 连 `localhost:3306`；采用 Docker Compose，而非本机安装 MySQL 作为默认方案。

## Goals / Non-Goals

**目标：**
- 用 Compose 一键提供 MySQL 8 开发实例
- Spring Boot 默认数据源指向该实例
- 文档写清启停顺序与 Navicat 连接参数
- 更新 OpenSpec 项目上下文中的数据库描述

**非目标：**
- 生产级高可用、备份、主从
- 从 H2 自动迁移历史数据
- 引入云托管 MySQL
- 改动日工时业务逻辑或 API 契约
- 本阶段强制引入 Flyway（可选，若成本低可顺带；非必须）

## Decisions

### 决策 1：MySQL 8 + Docker Compose

- 根目录 `docker-compose.yml`，服务名如 `mysql`
- 镜像端口 `3306:3306`
- 环境变量创建库（如 `sandbox`）与用户/密码（开发用固定弱口令，仅本地）
- 命名卷持久化数据，避免 `down` 后无谓丢库（`down -v` 才清数据）

**原因：** 可重复、不污染本机、与 Navicat/Spring 共用同一端口。  
**备选：** 本机安装 MySQL（用户未选）；云 RDS（过重）。

### 决策 2：Spring Boot 默认只连 MySQL

- `pom.xml`：移除默认 H2 runtime（或移出默认依赖）；加入 MySQL 驱动
- `application.yml`：`jdbc:mysql://localhost:3306/sandbox?...` + 用户名密码
- 暂不保留 H2 profile（保持简单）；测试若需要可后续再加 testcontainers/H2

**原因：** 默认路径唯一，避免「以为连了 MySQL 实际还在 H2」。  
**备选：** `dev-mysql` / `dev-h2` 双 profile（更灵活，也更绕）。

### 决策 3：表结构策略

- 短期继续 `ddl-auto: update`（或 `validate` + 手动），与现实体兼容
- Flyway 留作后续变更（本变更不阻塞）

**原因：** 换库为主，降低同时引入迁移工具的复杂度。

### 决策 4：文档与配置同步

- README：Docker Desktop 前置、`docker compose up -d`、等待就绪、再启后端、Navicat 参数表
- `openspec/config.yaml`：将「H2 文件库」改为「Compose MySQL 8」

### 决策 5：开发凭据

- 使用明确的本地开发账号（如 root/应用用户 + 简单密码），写在 README 与 compose 环境变量
- 不提交真实生产密钥；本地弱口令可接受

## Risks / Trade-offs

- [本机 3306 已被占用] → 缓解：README 说明冲突时改映射端口并同步改 JDBC
- [忘记先起 Compose 导致启动失败] → 缓解：文档强调启动顺序；错误日志会指向连接拒绝
- [弱口令误用于非本地] → 缓解：文档标注仅限本地；后续可改环境变量注入
- [H2 旧数据丢失体感] → 缓解：说明需重新导入 `samples/work-hours-sample.csv`

## Migration Plan

1. 安装/确认 Docker Desktop  
2. `docker compose up -d`  
3. 更新后端依赖与配置后启动应用  
4. 重新导入示例 CSV（如需要）  
5. 回滚：还原 H2 配置与依赖，停掉 Compose（旧 H2 文件若仍在或可恢复）

## Open Questions

- 无（本地库名/账号在 apply 时采用常规默认值即可，并写入 README）
