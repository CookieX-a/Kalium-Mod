# Kalium - 绝对激进派渲染优化mod

> 口号：遇事不决，量子力学；渲染超时，直接摆烂。

## 特性

- ⚡ **渲染熔断器**：单帧渲染超过500ms自动丢弃，复用上一帧
- 🌐 **网络冻结**：丢帧时自动冻结移动/交互包，防止不同步
- 📜 **JavaScript API**：通过JS脚本在游戏内绘制高优先级UI覆盖层
- 🚫 **错误码体系**：统一错误报告，便于调试

## 下载

自行通过 [Releases](https://github.com/CookieX-a/Kalium-Mod/releases) 下载

## 使用

### JS使用

在游戏内通过 `/kalium js <script>` 执行JS脚本，或通过其他模组调用 `KaliumJSAPI.executeScript()`

### 渲染使用

自动开启，无需手动启动

## 许可协议

本项目使用 MIT LICENSE。