# personal-sandbox

个人模块化单体沙盒：Java/Spring 主轴 + Vue 前端，按 Modulith 模块扩展点子。

## 技术栈

- **Backend:** Java 21, Spring Boot 4.1, Spring Modulith, Maven, JPA, MySQL
- **Frontend:** Vue 3, TypeScript, Vite, Vue Router
- **第一业务模块:** `work-management`（日工时 CSV 导入与展示）

## 仓库结构

```
personal-sandbox/
├── backend/
│   ├── app/                 # Spring Boot 启动与组装
│   ├── platform/            # 共享平台模块
│   └── work-management/     # 工作管理业务模块
├── frontend/                # Vue 3 + Vite
├── samples/                 # 示例数据
└── openspec/                # 规格与变更
```

## 前置要求

- JDK 21（本机示例路径：`D:\MyTool\JAVA\jdk-21.0.12`）
- Maven 3.9+
- Node.js 20+ / npm
- 本机 MySQL 8（默认 `localhost:3306`）

## 本地 MySQL

后端默认连接本机 MySQL，库名 `sandbox`：

| 项 | 值 |
|----|-----|
| 主机 | `localhost` |
| 端口 | `3306` |
| 数据库 | `sandbox`（不存在时 JDBC 会尝试创建） |
| 用户名 | `root` |
| 密码 | `123456` |

请确保 MySQL 服务已启动，且 `root` 具备建库与建表权限。可用 Navicat、DBeaver 或命令行连接同一实例。

连接串（JDBC）：

```text
jdbc:mysql://localhost:3306/sandbox?serverTimezone=UTC&characterEncoding=utf8
```

## 启动后端

```powershell
$env:JAVA_HOME = "D:\MyTool\JAVA\jdk-21.0.12"
$env:Path = "$env:JAVA_HOME\bin;D:\MyTool\Maven\apache-maven-3.9.16\bin;$env:Path"
cd backend
mvn -DskipTests install
cd app
mvn spring-boot:run
```

- API 基址：`http://localhost:8080`
- 健康检查：`http://localhost:8080/actuator/health`

## 启动前端

```powershell
cd frontend
npm install
npm run dev
```

- 开发地址：`http://localhost:5173`
- Vite 已将 `/api` 代理到 `http://localhost:8080`

## 日工时导入

1. 打开前端「工作管理」页
2. 选择 CSV 文件（可用 `samples/work-hours-sample.csv`）
3. 点击「导入」，列表会刷新展示记录

CSV 表头：

```text
work_date,hours,label,note
```

- `work_date`：`YYYY-MM-DD`（必填）
- `hours`：数字（必填，>= 0）
- `label` / `note`：可选

API：

- `GET /api/work-hours` — 列表
- `POST /api/work-hours/import` — multipart 字段名 `file`
