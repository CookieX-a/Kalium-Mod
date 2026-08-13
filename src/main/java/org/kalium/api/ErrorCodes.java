package org.kalium.api;

public final class ErrorCodes {
    // 通用
    public static final int UNKNOWN = -1;

    // 核心渲染
    public static final int UPDATE_FAILED = -2;
    public static final int FRAME_BUFFER_CAPTURE_FAILED = -7;
    public static final int OUT_OF_MEMORY = -8;
    public static final int MIXIN_TARGET_NOT_FOUND = -9;
    public static final int SHADER_COMPILE_ERROR = -10;
    public static final int FRAME_DROP_RECOVERY_TIMEOUT = -14;

    // JS API
    public static final int JS_ENGINE_UNAVAILABLE = -3;
    public static final int JS_SCRIPT_EXECUTION_ERROR = -4;
    public static final int OVERLAY_RENDER_EXCEPTION = -5;
    public static final int API_BINDING_FAILED = -12;

    // Helper / Network
    public static final int NETWORK_FREEZE_CONFLICT = -6;
    public static final int KEEP_ALIVE_BLOCKED = -11;

    // UI 资源
    public static final int TEXTURE_LOAD_FAILED = -13;

    private ErrorCodes() {}

    public static String getDescription(int code) {
        return switch (code) {
            case UPDATE_FAILED -> "渲染帧超时(>500ms)";
            case JS_ENGINE_UNAVAILABLE -> "JavaScript引擎未找到";
            case JS_SCRIPT_EXECUTION_ERROR -> "JS脚本执行异常";
            case OVERLAY_RENDER_EXCEPTION -> "覆盖层绘制崩溃";
            case NETWORK_FREEZE_CONFLICT -> "网络冻结状态冲突";
            case FRAME_BUFFER_CAPTURE_FAILED -> "帧缓冲截图失败";
            case OUT_OF_MEMORY -> "内存不足";
            case MIXIN_TARGET_NOT_FOUND -> "模组版本不兼容";
            case SHADER_COMPILE_ERROR -> "着色器编译失败";
            case KEEP_ALIVE_BLOCKED -> "心跳包被拦截";
            case API_BINDING_FAILED -> "Kalium API绑定失败";
            case TEXTURE_LOAD_FAILED -> "纹理资源加载失败";
            case FRAME_DROP_RECOVERY_TIMEOUT -> "连续丢帧超时，强制恢复";
            default -> "未知错误";
        };
    }
}