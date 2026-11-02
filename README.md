# Swing 计算器与 Access 数据管理

Java 桌面课程项目，将四则运算、Access 数据库操作和父子窗体交互放在同一个 Swing 界面中，并集成 FlatLaf 主题、SVG 图标与 Toast 提示。它用于展示 GUI 事件处理、JDBC 数据访问和组件集成实践，不是可直接用于生产环境的数据管理系统。

## 功能概览

| 模块 | 当前代码实现 |
| --- | --- |
| 计算器 | 两个操作数的加、减、乘、除；结果格式化与输入提示 |
| Access 管理 | 查找或指定 `.mdb` 路径，连接数据库，展示、添加、修改、删除记录 |
| 条件查询 | 按姓名输入执行 `LIKE` 查询；要进行模糊匹配，需提供相应的 SQL 通配模式 |
| 父子窗体 | 主窗体传递姓名、学号，子窗体编辑并将结果回传 |
| 界面体验 | FlatLaf 外观、SVG 图标、通知提示、文本框复制/粘贴/剪切菜单 |

## 技术栈

- Java、Swing、JDBC；源码使用 switch 表达式，建议 JDK 17 或更高版本。
- FlatLaf 3.2.5 与 FlatLaf Extras。
- UCanAccess 5.0.0、HSQLDB 2.5.0、Jackcess 3.0.1。
- 仓库 `lib/` 还包含 Commons Lang、Commons Logging、jSVG 与 SVG Salamander。
- 使用 Eclipse / IntelliJ 工程配置；仓库没有可直接使用的 Maven 或 Gradle 构建文件。

## 目录与入口

```text
src/main/java/source/
  FrameMain202209160316.java     主窗体、计算器、数据库事件处理和 main 入口
  FrameNext202209160316.java     子窗体及数据回传
  MElements.java                自定义文本组件
  Fontloader.java               字体加载与回退
src/utils/java/notify/          通知组件集成
src/utils/java/svgloader/       SVG 加载与图标资源
lib/                           第三方 JAR
fonts/                         字体资源
DB316.mdb                      历史示例数据库
META-INF/MANIFEST.MF            主类清单
```

主类为 `source.FrameMain202209160316`。原目录、类名和仓库 slug 保持不变，避免破坏现有 IDE 配置与链接。

## 本地运行

```powershell
git clone https://github.com/baishang-ui/java_project.git
cd java_project
```

1. 安装可正常执行 `java -version` 和 `javac -version` 的 JDK 17+。
2. 用 IntelliJ IDEA 或 Eclipse 打开仓库，配置 `src/main/java` 与 `src/utils/java` 为源码根目录。
3. 将 `lib/` 中的 JAR 加入编译和运行 classpath。
4. **另外提供 JetBrains Annotations 依赖**：`FrameMain202209160316.java` 引用了 `org.jetbrains.annotations.NotNull`，但当前 `lib/` 未包含该类型。可由 IDE 配置 `org.jetbrains:annotations`，不要依赖开发机偶然存在的全局类路径。
5. 将 SVG 资源保留在运行 classpath 的 `svgloader/svgfiles/` 中；确保工作目录为仓库根目录，以便字体和数据库路径正确解析。
6. 运行 `source.FrameMain202209160316.main()`。

如果从命令行编译，需要先将 Annotations JAR 放入 `lib/`，再执行：

```powershell
New-Item -ItemType Directory -Path build/classes -Force | Out-Null
$sources = Get-ChildItem src -Recurse -Filter *.java | ForEach-Object { '"' + $_.FullName + '"' }
$sources | Set-Content -Encoding utf8 sources.txt
javac -encoding UTF-8 -cp "lib/*" -d build/classes '@sources.txt'
New-Item -ItemType Directory -Path build/classes/svgloader/svgfiles -Force | Out-Null
Copy-Item src/utils/java/svgloader/svgfiles/*.svg build/classes/svgloader/svgfiles/
java -cp "build/classes;lib/*" source.FrameMain202209160316
```

上面的 classpath 分隔符适用于 Windows。此步骤说明来自源码和资源路径核对，不表示已在所有平台验证构建成功。

## 数据库使用与安全边界

程序围绕 `tb_test` 表操作，代码使用 `RowID`、`userName`、`userNum`、`comment` 字段。请只在独立副本中使用演示数据；不要直接连接真实业务或人员数据库。

- 添加、更新、删除操作会真实修改所选 `.mdb` 文件。运行前先备份，测试时选择数据库副本。
- 当前代码通过字符串拼接构造 SQL，尚未改为 `PreparedStatement`，不能将此实现描述为安全的生产级输入处理。
- 错误处理、连接生命周期和边界输入仍需完善；分母为零等情况应补充专门验证。
- 历史数据库、IDE 工程和程序标题可能包含课程时期的个人标识。公开演示应使用脱敏副本，不应把真实记录截图放进作品集。

## 验证状态

- 已核对源码入口、依赖目录、资源定位和数据库字段使用方式。
- 仓库未发现自动化测试套件；完整 GUI 操作和数据库增删改查仍需在隔离副本中验证。
- 本次整理环境中的系统 `javac` 启动即异常退出，未获得有效编译结果，因此不声明构建或运行通过；没有为获得成功结果修改系统 JDK 或项目业务代码。

## 第三方组件与许可

FlatLaf、UCanAccess 及其他 JAR 属于第三方依赖。`notify` 和图标工具属于组件集成部分，整理文档时不将其来源未确认的实现包装为原创组件库。复用或分发前应核对这些组件、字体和图标的许可证。

当前仓库未提供统一的根目录 `LICENSE`，因此不能自行宣称整个项目采用 MIT 或其他开源许可。若计划对外分发，请先明确作者授权及依赖许可。

## 仓库链接

[baishang-ui/java_project](https://github.com/baishang-ui/java_project)
