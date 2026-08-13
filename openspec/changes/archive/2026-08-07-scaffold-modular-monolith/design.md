## Context

仓库为从零开始的绿场项目（起初仅有 `openspec/`）。动机见 `proposal.md`。规格覆盖骨架可运行性，以及日工时导入与展示；导入文件的具体列细节在第一刀刻意保持灵活。

探索阶段已确认的约束：
- 模块化单体（不做微服务拆分）
- Java + Maven + Spring Boot 4.1 + Spring Modulith
- Vue 3 + TypeScript + Vite
- 第一业务能力：导入日工时并展示

## Goals / Non-Goals

**目标：**
- 建立带清晰 Modulith 边界的 Maven 多模块后端
- 建立可通过 HTTP 调用后端的 Vue 前端
- 打通日工时「导入 → 持久化 → 列表 → 展示」整条链路
- 首版导入格式足够简单，不阻塞脚手架落地

**非目标：**
- 登录鉴权、多用户权限或协作
- 复杂统计分析、图表或工时编辑工作流
- 敲定长期导入模板 / Excel 标准
- Docker/K8s 生产部署加固
- 拆成多个可独立部署服务

## Decisions

### 决策 1：仓库布局

```
personal-sandbox/
├── backend/                 # Maven 父工程 + 子模块
│   ├── pom.xml
│   ├── app/                 # Spring Boot 启动入口
│   ├── platform/            # 共享内核（后续可放公共配置、异常等）
│   └── work-management/     # 第一业务模块
├── frontend/                # Vue 3 + TS + Vite
└── README.md
```

**原因：** Java 与 Vue 工具链相互独立，同时单一仓库保持「个人沙盒」的一体感。  
**备选：** 单 Maven 模块（边界更弱）；Nx/Turborepo 等多语言 monorepo 工具（现阶段过重）。

### 决策 2：后端技术栈

- Java 21、Spring Boot 4.1.x、Spring Modulith
- Spring Web、Validation、Data JPA
- 开发期持久化：H2 文件库，降低首次启动门槛；表结构由 Hibernate `ddl-auto` 管理
- 按包划分模块，Modulith 校验 `platform` ← `work-management` 依赖（由 `app` 组装）

**原因：** 贴合学习主轴与当前 Boot 4.x 稳定线；H2 避免首个垂直切片被 Docker 卡住。  
**备选：** 第一天就上 PostgreSQL（更接近生产，冷启动更慢）；Spring Boot 3.5（不符合「学新」目标）。

### 决策 3：前端技术栈与对接方式

- Vue 3 + TypeScript + Vite + Vue Router
- 开发期：Vite 将 `/api` 代理到后端 `http://localhost:<port>`
- 首个界面：工作管理页（文件上传 + 记录表格/列表）

**原因：** 满足「前端也不能少」，同时仍可按模块化单体演进（日后也可由 Boot 托管 `dist`）。  
**备选：** Thymeleaf/HTMX（现代前端练手不足）；React（用户已选 Vue）。

### 决策 4：日工时导入约定（务实默认）

- 首版接受 CSV 上传
- 每行持久化为日工时记录（至少：工作日期 + 工时；可选备注/标签）
- 具体 CSV 表头在实现与 README 中固定，不写死在规格里，便于后续演进
- API 使用 `/api/work-hours`（导入 + 列表）

**原因：** 用户暂缓细节讨论；CSV 是最轻量、最常见的批量导入方式。规格保持「合法导入文件」层面的抽象。  
**备选：** 仅支持 Excel（依赖更重）；粘贴 JSON（「导入一份记录」体感较弱）。

### 决策 5：API 风格

- 列表用简单 REST JSON；导入用 multipart 文件
- 本地学习脚手架暂不鉴权（明确的临时非目标）

**原因：** 最快验证模块切片。鉴权可后续放入 `platform`。

## Risks / Trade-offs

- [现在用 H2，以后换 Postgres] → 缓解：保持 JPA 实体可移植；需要时通过配置切换数据源
- [CSV 表头可能变更] → 缓解：在 README 写清首版表头；解析逻辑集中在 `work-management`
- [导入 API 无鉴权] → 缓解：仅适合个人本地使用；对外暴露前再加 Security
- [Modulith 学习曲线] → 缓解：先两个模块 + 启动组装；事件等能力以后再加深

## Migration Plan

- 绿场项目：无生产数据迁移
- 回滚：通过 git 回退或移除相关模块即可恢复

## Open Questions

- 若你已有惯用 CSV 表头/样例文件，可再对齐（当前已有默认样例）
- 本地默认端口是否固定 8080（实现已采用 8080）
