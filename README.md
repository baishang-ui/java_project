# 简单计算器与数据库管理系统

> **作者**: 毕天宇 | **学号**: 202209160316  
> 基于 Java Swing 与 FlatLaf 的桌面应用程序，集成了四则运算计算器与 Access 数据库 CRUD 管理功能。

---

## 📋 目录

- [项目简介](#项目简介)
- [功能特性](#功能特性)
- [技术架构](#技术架构)
- [项目结构](#项目结构)
- [依赖库说明](#依赖库说明)
- [环境要求](#环境要求)
- [快速开始](#快速开始)
- [使用指南](#使用指南)
- [模块说明](#模块说明)
- [构建与打包](#构建与打包)
- [许可证](#许可证)

---

## 项目简介

本项目是一个 Java Swing 桌面应用程序，主要包含两大核心功能模块：

1. **四则运算计算器** — 支持加、减、乘、除基本运算，结果保留两位小数
2. **Access 数据库管理** — 通过 UCanAccess 驱动连接 Access (.mdb) 数据库，实现数据表的增删改查

界面采用 **FlatLaf IntelliJ** 主题美化，使用 SVG 图标和 **PingFang-Bold** 字体，并提供 **Toast 通知**组件实时反馈操作结果。

---

## 功能特性

### 🧮 计算器模块
- 两个操作数的 **加 / 减 / 乘 / 除** 四则运算
- 运算符通过下拉框选择（`+` `-` `*` `/`）
- 结果自动格式化，保留两位小数
- 输入校验与错误提示

### 🗃️ 数据库管理模块
- 自动扫描程序目录下的 `.mdb` 数据库文件
- 支持手动输入或修改数据库文件路径
- **打开/连接** Access 数据库
- **刷新** 数据库连接状态
- **查询** — 按姓名模糊搜索数据记录
- **添加** — 插入新的姓名与学号记录
- **更新** — 按 RowID 修改已存在的记录
- **删除** — 按 RowID 删除指定记录
- **清空** — 一键清空所有输入框

### 🪟 父子窗体交互
- 主窗体向子窗体传递参数（姓名、学号）
- 子窗体可修改数据并返回给主窗体
- 返回结果显示在主窗体日志区域

### 🔔 通知系统
- 自定义 Toast 弹出通知组件
- 支持 4 种类型：`SUCCESS`、`INFO`、`WARNING`、`ERROR`
- 支持 6 个显示位置：`TOP_LEFT`、`TOP_CENTER`、`TOP_RIGHT`、`BOTTOM_LEFT`、`BOTTOM_CENTER`、`BOTTOM_RIGHT`
- 带淡入淡出动画效果
- 显示时间戳信息

### 🎨 UI 美化
- **FlatLaf IntelliJ** 现代扁平化外观主题
- **SVG 图标** — 16 个 FontAwesome 风格图标，支持颜色滤镜
- **PingFang-Bold** 自定义字体
- 圆角组件（按钮、文本框、复选框、滚动条）
- 右键菜单支持（复制/粘贴/剪切）

---

## 技术架构

```
┌─────────────────────────────────────────────────────┐
│                   表现层 (UI Layer)                    │
│  FrameMain202209160316  │  FrameNext202209160316     │
│  (主窗体 - 计算器 + 数据库管理)  │  (子窗体 - 数据回传)        │
├─────────────────────────────────────────────────────┤
│                   组件层 (Component Layer)             │
│  MElements (自定义组件)  │  Toast通知组件  │  SVG图标组件  │
│  MJTextArea  MJTextField│  Notifications │  SvgUtils    │
├─────────────────────────────────────────────────────┤
│                   工具层 (Utility Layer)               │
│  Fontloader (字体加载)  │  SvgIcon (SVG封装)          │
├─────────────────────────────────────────────────────┤
│                   数据层 (Data Layer)                  │
│  UCanAccess JDBC Driver → Access (.mdb) Database    │
└─────────────────────────────────────────────────────┘
```

**技术栈**:
- **语言**: Java (JDK 17+)
- **GUI 框架**: Java Swing
- **主题**: FlatLaf 3.2.5 (IntelliJ 主题)
- **数据库驱动**: UCanAccess 5.0.0 + HSQLDB 2.5.0
- **数据库文件**: MS Access `.mdb` 格式
- **SVG 渲染**: jSVG 1.3.0 + SVG Salamander 1.1.4
- **构建工具**: Eclipse IDE (无 Maven/Gradle)

---

## 项目结构

```
lyu202209160316/
├── src/
│   ├── main/java/source/           # 主程序源代码
│   │   ├── FrameMain202209160316.java   # 主窗体 (计算器 + 数据库管理)
│   │   ├── FrameNext202209160316.java   # 子窗体 (父子窗体交互)
│   │   ├── MElements.java               # 自定义 Swing 组件
│   │   └── Fontloader.java              # 自定义字体加载器
│   │
│   └── utils/java/                 # 工具模块
│       ├── notify/                 # Toast 通知系统
│       │   ├── Notifications.java          # 通知管理器 (单例)
│       │   └── ToastClientProperties.java  # 客户端属性定义
│       │   └── ui/
│       │       ├── render/
│       │       │   ├── ToastNotificationPanel.java  # 通知面板
│       │       │   ├── ToastPanelUI.java            # 面板 UI 委托
│       │       │   └── DropShadowBorder.java        # 阴影边框
│       │       └── util/
│       │           ├── NotificationHolder.java      # 通知持有者
│       │           ├── ShadowRenderer.java          # 阴影渲染器
│       │           └── UIUtils.java                 # UI 工具类
│       │
│       └── svgloader/              # SVG 图标加载系统
│           ├── loaderfunc/
│           │   ├── SvgIcon.java    # SVG 图标封装类
│           │   └── SvgUtils.java   # SVG 工具类 (资源加载)
│           └── svgfiles/
│               ├── Res.java        # 资源定位类
│               └── *.svg           # 16 个 SVG 图标文件
│
├── lib/                            # 第三方依赖库 (JAR)
│   ├── flatlaf-3.2.5.jar          # FlatLaf 主题库
│   ├── flatlaf-extras-3.2.5.jar   # FlatLaf 扩展 (SVG 支持)
│   ├── ucanaccess-5.0.0.jar       # MS Access JDBC 驱动
│   ├── hsqldb-2.5.0.jar           # HSQLDB 嵌入式数据库
│   ├── jackcess-3.0.1.jar         # Access 文件读写库
│   ├── commons-lang3-3.8.1.jar    # Apache Commons Lang
│   ├── commons-logging-1.2.jar    # Apache Commons Logging
│   ├── jsvg-1.3.0.jar            # SVG 渲染引擎
│   └── svgSalamander-1.1.4.jar   # SVG 解析库
│
├── fonts/
│   └── PingFang-Bold.ttf          # 界面字体文件
│
├── DB316.mdb                      # 示例 Access 数据库文件
├── META-INF/
│   └── MANIFEST.MF                # JAR 清单文件 (主类入口)
│
├── jar2exe_new.exe4j              # exe4j 打包配置文件
├── mainCalculator.eml             # Eclipse 模块文件
├── mainCalculator.iml             # IntelliJ IDEA 模块文件
├── .project                       # Eclipse 项目文件
├── .classpath                     # Eclipse 类路径配置
└── .idea/                         # IntelliJ IDEA 配置
    ├── artifacts/                 # 构建产物配置
    └── libraries/                 # 库引用配置
```

---

## 依赖库说明

| 依赖库 | 版本 | 用途 |
|--------|------|------|
| **FlatLaf** | 3.2.5 | 现代扁平化 Look & Feel 主题 |
| **FlatLaf Extras** | 3.2.5 | FlatLaf 扩展，提供 SVG 图标支持 |
| **UCanAccess** | 5.0.0 | 纯 Java 实现的 MS Access JDBC 驱动 |
| **HSQLDB** | 2.5.0 | UCanAccess 的底层 SQL 引擎 |
| **Jackcess** | 3.0.1 | MS Access 数据库文件读写库 |
| **Commons Lang3** | 3.8.1 | Apache 通用工具库 |
| **Commons Logging** | 1.2 | Apache 日志门面 |
| **jSVG** | 1.3.0 | 轻量级 SVG 渲染引擎 |
| **SVG Salamander** | 1.1.4 | SVG 解析与处理库 |

---

## 环境要求

| 环境 | 要求 |
|------|------|
| **JDK** | 17 或更高版本 |
| **操作系统** | Windows / macOS / Linux |
| **IDE** | Eclipse / IntelliJ IDEA（推荐） |
| **数据库** | MS Access .mdb 文件（项目自带 `DB316.mdb`） |

---

## 快速开始

### 1. 克隆项目

```bash
git clone <repository-url>
cd lyu202209160316
```

### 2. 导入 IDE

**Eclipse**:
- `File → Import → Existing Projects into Workspace`
- 选择项目根目录，点击完成

**IntelliJ IDEA**:
- `File → Open`，选择项目根目录
- IDEA 会自动识别 `.iml` 模块文件

### 3. 配置依赖

确保 `lib/` 目录下的所有 JAR 文件已添加到 Build Path：
- Eclipse: 右键项目 → `Build Path → Configure Build Path → Libraries → Add JARs`
- IntelliJ: `File → Project Structure → Libraries → + → Java → 选择 lib/ 目录`

### 4. 运行程序

主入口类位于 [FrameMain202209160316.java](src/main/java/source/FrameMain202209160316.java)，运行其 `main` 方法：

```java
public static void main(String[] args) {
    FlatIntelliJLaf.setup();
    FrameMain202209160316 fr = new FrameMain202209160316();
    // ...
}
```

### 5. 数据库配置

程序启动后会自动扫描运行目录下的 `.mdb` 文件。确保 `DB316.mdb` 与程序在同一目录下，或手动在界面中输入数据库文件路径。

---

## 使用指南

### 计算器操作

1. 在左侧第一个输入框中输入 **第一个操作数**
2. 通过下拉框选择 **运算符**（`+` `-` `*` `/`）
3. 在第二个输入框中输入 **第二个操作数**
4. 点击 **计算** 按钮，结果显示在下方的只读文本框中

### 数据库操作

| 按钮 | 功能 | 说明 |
|------|------|------|
| **打开** | 连接数据库 | 使用界面中的路径连接 Access 数据库 |
| **刷新** | 重连数据库 | 重新连接并刷新数据表显示 |
| **查询** | 按姓名搜索 | 根据"姓名"输入框内容进行模糊查询 |
| **添加** | 插入记录 | 使用"姓名"和"学号"输入框添加新记录 |
| **更新** | 修改记录 | 按 RowID 更新对应记录的姓名和学号 |
| **删除** | 删除记录 | 按 RowID 删除指定记录 |
| **清空** | 清空输入 | 清空姓名、学号、ID 三个输入框 |

> 💡 **提示**: 点击数据表中的某一行，会自动填充到右侧的 ID、姓名、学号输入框中，方便进行更新或删除操作。

### 日志区域

程序底部有一个滚动日志区域，用于显示：
- 当前工作目录
- 自动发现的 `.mdb` 文件
- 父窗体接收到的子窗体返回值

### 右键菜单

自定义文本框（`MJTextField`）和文本域（`MJTextArea`）支持右键菜单，提供：
- **复制** (`Ctrl+C`)
- **粘贴** (`Ctrl+V`)
- **剪切** (`Ctrl+X`)

---

## 模块说明

### 主窗体 [FrameMain202209160316](src/main/java/source/FrameMain202209160316.java)

核心窗体类，包含所有主要业务逻辑：

- `calc()` — 四则运算计算
- `OpenDB()` — 打开/连接 Access 数据库
- `ShowAll()` — 将 SQL 查询结果渲染到 JTable
- `excuteSQL()` — 执行 SQL 查询
- `CreateNotification()` — 创建 Toast 通知
- `FindFiles()` — 递归搜索指定类型的文件
- `BetterDouble()` — 双精度浮点数格式化

### 子窗体 [FrameNext202209160316](src/main/java/source/FrameNext202209160316.java)

子窗体类，用于演示父子窗体数据交互：
- 接收主窗体传递的姓名和学号
- 允许用户修改信息
- 点击"返回"后将修改后的数据传回主窗体

### 自定义组件 [MElements](src/main/java/source/MElements.java)

自定义 Swing 组件扩展：

- **MJTextArea** — 带右键菜单的多行文本域，支持复制/粘贴/剪切
- **MJTextField** — 带右键菜单的单行文本框，支持复制/粘贴/剪切

### 通知系统 [Notifications](src/utils/java/notify/Notifications.java)

完整的 Toast 通知系统，采用单例模式：

- `Type` 枚举 — `SUCCESS` | `INFO` | `WARNING` | `ERROR`
- `Location` 枚举 — 6 种屏幕定位
- `show()` — 显示通知消息
- `clear()` / `clearAll()` — 清除通知
- 支持淡入淡出动画、消息队列、位置堆叠

### SVG 图标系统 [SvgUtils](src/utils/java/svgloader/loaderfunc/SvgUtils.java)

基于 FlatLaf 的 SVG 图标加载工具：

- `getSvg(name)` — 加载 SVG 图标（原始颜色）
- `getSvg(name, color)` — 加载 SVG 图标并应用颜色滤镜
- `getSvg(name, color, width, height)` — 加载并缩放 SVG 图标

**内置图标列表**:

| 图标 | 文件名 | 用途 |
|------|--------|------|
| 🧮 `calculator` | calculator-solid.svg / calculator.svg | 应用图标 |
| 🟰 `equals` | equals-solid.svg | 计算按钮 |
| 👶 `baby` | baby-solid.svg | 子窗体按钮 |
| 👁️ `eye` | eye-solid.svg | 显示按钮 |
| 📂 `folder-open` | folder-open-solid.svg | 打开/刷新按钮 |
| 🔍 `search` | search-solid.svg | 查询按钮 |
| ➕ `plus` | plus-solid.svg | 添加按钮 |
| 📥 `file-import` | file-import-solid.svg | 更新按钮 |
| 🔄 `sync` | sync-solid.svg | 刷新按钮 |
| ✖️ `times` | times-solid.svg | 清空按钮 |
| ➖ `minus-circle` | minus-circle-solid.svg | 删除按钮 |
| 📍 `location-arrow` | location-arrow-solid.svg | 数据库路径标签 |
| ✍️ `signature` | signature-solid.svg | 姓名标签 |
| 📋 `list-ol` | list-ol-solid.svg | 学号标签 |
| 🆔 `address-card` | address-card-regular.svg | ID 标签 |

---

## 构建与打包

### Eclipse 导出 JAR

1. `File → Export → Java → Runnable JAR file`
2. 选择启动配置: `FrameMain202209160316`
3. 选择导出目标路径
4. 选择 "Package required libraries into generated JAR"

### IntelliJ IDEA 构建

项目已配置 Artifact ([calculator_project_new_jar.xml](.idea/artifacts/calculator_project_new_jar.xml))：

1. `Build → Build Artifacts → calculator_project_new:jar → Build`
2. 构建产物输出到 `out/artifacts/calculator_project_new_jar/`

### MANIFEST.MF

```
Manifest-Version: 1.0
Main-Class: source.FrameMain202209160316
```

### exe4j 打包

项目包含 `jar2exe_new.exe4j` 配置文件，可使用 [exe4j](https://www.ej-technologies.com/products/exe4j/overview.html) 工具将 JAR 包转为 Windows `.exe` 可执行文件。

---

## 数据库结构

示例数据库 `DB316.mdb` 包含一张数据表：

**tb_test**

| 列名 | 类型 | 说明 |
|------|------|------|
| RowID | INT (自增) | 主键，记录唯一标识 |
| userName | VARCHAR | 用户姓名 |
| userNum | VARCHAR | 用户学号 |
| comment | VARCHAR | 备注信息 |

---

## 许可证

本项目仅用于学习与演示目的。

---

> 🤖 毕天宇 202209160316 — Java 课程设计项目
