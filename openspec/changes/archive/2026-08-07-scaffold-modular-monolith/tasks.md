## 1. 后端 Maven 骨架

- [x] 1.1 创建 `backend/` Maven 父工程（Java 21、Spring Boot 4.1.x），包含模块 `app`、`platform`、`work-management`
- [x] 1.2 实现 `platform` 模块包骨架与共享基线（可为空或仅含最小共享类型）
- [x] 1.3 实现 `app` Spring Boot 启动入口，依赖 `platform` 与 `work-management`，配置 H2 数据源与健康/就绪端点
- [x] 1.4 配置 Spring Modulith 模块边界，使 `work-management` 成为独立应用模块

## 2. 日工时后端能力

- [x] 2.1 新增日工时记录的 JPA 实体/仓库（日期 + 工时，可选备注/标签）
- [x] 2.2 实现 CSV 导入服务与 `/api/work-hours` 下的 multipart 导入 API（拒绝非法文件）
- [x] 2.3 实现列表 API，返回已持久化的日工时记录（无数据时返回空列表）
- [x] 2.4 添加示例 CSV，并在文档中说明表头以便本地测试

## 3. Vue 前端应用

- [x] 3.1 搭建 `frontend/`：Vue 3 + TypeScript + Vite + Vue Router
- [x] 3.2 配置 Vite 开发代理，将 `/api` 转发到后端
- [x] 3.3 实现工作管理页：文件上传导入 + 记录表格/列表
- [x] 3.4 导入成功后，从列表 API 刷新展示数据

## 4. 文档与项目上下文

- [x] 4.1 编写根目录 `README.md`，包含前置要求与前后端启动命令
- [x] 4.2 更新 `openspec/config.yaml` 的 context，写入约定技术栈与模块布局
- [x] 4.3 冒烟验证：后端可启动、前端可启动、可导入示例 CSV、界面可见记录
