# OpenSpec 使用说明

本仓库用 [OpenSpec](https://openspec.dev/) 做「先规格、再实现」的变更管理。文档默认中文（见 `openspec/config.yaml`）。

---

## 1. 目录结构

```
personal-sandbox/
├── openspec/
│   ├── config.yaml          # 项目上下文、文档约定（给 AI / 工作流读）
│   ├── specs/               # 主规格：已落地能力的行为契约
│   │   ├── modular-monolith-foundation/
│   │   │   └── spec.md
│   │   └── work-hours/
│   │       └── spec.md
│   └── changes/             # 进行中的变更
│       ├── <change-name>/   # 活跃变更（实现中 / 未归档）
│       │   ├── proposal.md  # 为什么做、做什么
│       │   ├── design.md    # 技术上怎么做
│       │   ├── tasks.md     # 实现勾选清单
│       │   ├── specs/       # 本变更的规格增量（delta）
│       │   └── .openspec.yaml
│       └── archive/         # 已归档变更（按日期前缀保存）
│           └── 2026-08-07-scaffold-modular-monolith/
├── .cursor/
│   ├── commands/opsx-*.md   # Cursor 里可用的 /opsx-* 指令
│   └── skills/openspec-*    # 对应技能说明
└── OPENSPEC.md              # 本文件
```

### 各部分含义

| 路径 | 作用 |
|------|------|
| `openspec/specs/` | **主规格**：项目当前应遵守的行为约定（归档后从变更同步进来） |
| `openspec/changes/<name>/` | **活跃变更**：一次功能/修复的完整规划与实现清单 |
| `openspec/changes/<name>/specs/` | **增量规格（delta）**：相对主规格的新增/修改/删除 |
| `openspec/changes/archive/` | **历史记录**：做完归档后的变更快照 |
| `openspec/config.yaml` | 技术栈、目录约定、中文撰写等上下文 |

### 变更内四个核心文件

| 文件 | 回答的问题 |
|------|------------|
| `proposal.md` | 为什么做、范围与影响 |
| `specs/**/spec.md` | 系统必须表现出什么行为（可验证场景） |
| `design.md` | 技术选型、模块边界、取舍 |
| `tasks.md` | 按勾选框推进的实现步骤 |

---

## 2. 推荐工作流

```
不清楚 / 要讨论细节     →  /opsx-explore
方向清楚，要开新变更   →  /opsx-propose
按 tasks 写代码         →  /opsx-apply
对照规格检查实现       →  /opsx-verify   （可选）
全部做完收尾           →  /opsx-archive
```

示意：

```
explore ──▶ propose ──▶ apply ──▶ (verify) ──▶ archive
              │                      │
              │                      └─ 代码落地、勾选 tasks
              └─ 生成 proposal / specs / design / tasks
```

归档时通常会把变更里的 delta specs **同步进** `openspec/specs/`，再把变更目录移到 `changes/archive/`。

---

## 3. 常用 Cursor 指令

在聊天里输入斜杠命令即可（也可口述「帮我 propose / apply / archive」）。

### 核心流程

| 指令 | 用途 |
|------|------|
| `/opsx-explore` | 探索模式：想清楚问题、比方案；默认不写业务代码 |
| `/opsx-propose <名称>` | 新建变更，并一次生成 proposal / specs / design / tasks |
| `/opsx-apply [名称]` | 按 `tasks.md` 实现；全部完成后可归档 |
| `/opsx-verify [名称]` | 核对实现是否符合制品与规格 |
| `/opsx-archive [名称]` | 归档已完成变更，并可同步主规格 |

### 补充指令

| 指令 | 用途 |
|------|------|
| `/opsx-new <名称>` | 只创建变更容器，再逐步补制品 |
| `/opsx-continue [名称]` | 继续补全未写完的制品 |
| `/opsx-ff <名称>` | 快进：快速生成全部规划制品 |
| `/opsx-update` | 修订已有规划制品，保持彼此一致（不写业务代码） |
| `/opsx-sync` | 仅把 delta specs 合并进主规格（不归档） |
| `/opsx-bulk-archive` | 批量归档多个已完成变更 |
| `/opsx-onboard` | 带教走完一轮完整 OpenSpec 流程 |

名称一般为 **kebab-case**，例如：`improve-work-hours-import`。

---

## 4. 和本仓库相关的现状

- 首个变更 `scaffold-modular-monolith` 已实现并归档：  
  `openspec/changes/archive/2026-08-07-scaffold-modular-monolith/`
- 主规格已具备：
  - `modular-monolith-foundation`（模块化单体骨架）
  - `work-hours`（日工时导入与展示）
- 当前若无活跃变更，`openspec list` 会显示空列表；下一件事请用 `/opsx-propose` 新开变更。

---

## 5. 怎么跟 AI 说清楚

**探索（还没定方案）：**

```text
/opsx-explore
日工时导入不满意：……（现状问题）
我希望：……（期望行为）
样例文件：……（可选）
```

**立项（要改代码前先出文档）：**

```text
/opsx-propose improve-work-hours-import
……把范围、非目标、验收点说清楚
```

**落地：**

```text
/opsx-apply
```

**收尾：**

```text
/opsx-archive
```

---

## 6. CLI 速查（可选）

本机已安装 OpenSpec CLI 时，也可在项目根目录执行：

```powershell
openspec list --json
openspec status --change "<name>"
openspec status --change "<name>" --json
```

日常在 Cursor 里用 `/opsx-*` 即可，不必每次手敲 CLI。

---

## 7. 约定提醒

- 规格写**可观察行为**（WHEN/THEN），少写类名、框架细节（细节放 `design.md`）。
- 代码标识符、路径、API 可保持英文；OpenSpec 文章面默认中文。
- 大改行为时优先**新开变更**，不要在已归档变更上直接改历史。
- 建议尽早使用 Git，便于误操作回退与变更对照。
