## 1. Spring Boot 数据源切换

- [x] 1.1 在 `backend/app` 将默认依赖从 H2 改为 MySQL 驱动，并移除默认 H2 Console 配置
- [x] 1.2 更新 `application.yml`，使默认数据源指向 `localhost:3306/sandbox`，账号 `root`
- [x] 1.3 在本机 MySQL 已启动的前提下验证后端可启动，`/actuator/health` 为 UP

## 2. 文档与项目上下文

- [x] 2.1 更新 `README.md`：本机 MySQL 前置、连接参数、启动顺序、Navicat/JDBC 连接说明
- [x] 2.2 更新 `openspec/config.yaml`，将默认数据库描述改为本机 MySQL

## 3. 冒烟（可选）

- [x] 3.1 本机 MySQL 已启动 → 启后端 →（可选）导入示例 CSV / 用客户端连 `localhost:3306` 可见数据
