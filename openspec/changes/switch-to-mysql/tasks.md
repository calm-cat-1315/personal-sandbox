## 1. Docker Compose MySQL

- [ ] 1.1 在仓库根目录新增 `docker-compose.yml`，定义 MySQL 8 服务、端口 `3306`、开发库与账号、数据卷
- [ ] 1.2 将 Compose 相关忽略项（若有本地覆盖文件）纳入 `.gitignore` 约定，并确认 `docker compose config` 可通过

## 2. Spring Boot 数据源切换

- [ ] 2.1 在 `backend/app` 将默认依赖从 H2 改为 MySQL 驱动，并移除默认 H2 Console 配置
- [ ] 2.2 更新 `application.yml`，使默认数据源指向 `localhost:3306` 上的开发库
- [ ] 2.3 在 MySQL 已启动的前提下验证后端可启动，`/actuator/health` 为 UP

## 3. 文档与项目上下文

- [ ] 3.1 更新 `README.md`：Docker 前置、Compose 启停、启动顺序、Navicat/JDBC 连接说明
- [ ] 3.2 更新 `openspec/config.yaml`，将默认数据库描述改为 Compose MySQL 8
- [ ] 3.3 冒烟：Compose 起库 → 启后端 →（可选）导入示例 CSV / 用客户端连 `localhost:3306` 可见数据
