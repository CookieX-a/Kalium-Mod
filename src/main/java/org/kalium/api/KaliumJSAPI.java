package org.kalium.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.kalium.FrameDropGuard;
import org.kalium.Kalium;
import org.kalium.helper.ChatHelper;
import org.kalium.helper.NetworkFreezeHelper;

import javax.script.*;
import java.util.concurrent.ConcurrentHashMap;

public class KaliumJSAPI {
    private static ScriptEngine engine;
    private static boolean isAvailable = false;
    private static final ConcurrentHashMap<String, Object> bindings = new ConcurrentHashMap<>();

    static {
        warmup();
    }

    public static void warmup() {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            engine = manager.getEngineByName("graal.js");
            if (engine == null) {
                engine = manager.getEngineByName("nashorn");
            }
            if (engine == null) {
                Kalium.LOGGER.error("未找到JS引擎！请安装GraalVM");
                isAvailable = false;
                return;
            }

            Bindings global = engine.getBindings(ScriptContext.ENGINE_SCOPE);
            global.put("Kalium", createJSBridge());
            isAvailable = true;
            Kalium.LOGGER.info("JS引擎初始化成功: {}", engine.getFactory().getEngineName());
        } catch (Exception e) {
            Kalium.LOGGER.error("JS引擎初始化失败", e);
            isAvailable = false;
        }
    }

    private static Object createJSBridge() {
        return new Object() {
            @SuppressWarnings("unused")
            public void drawText(String text, int x, int y, String colorHex) {
                try {
                    int color = Integer.parseInt(colorHex.replace("#", ""), 16);
                    OverlayRenderer.addOverlay((context, tick) -> {
                        context.drawString(Minecraft.getInstance().font, text, x, y, color);
                    });
                } catch (Exception e) {
                    Kalium.LOGGER.error("drawText 执行失败", e);
                }
            }

            @SuppressWarnings("unused")
            public void drawRect(int x1, int y1, int x2, int y2, String colorHex) {
                try {
                    int color = Integer.parseInt(colorHex.replace("#", ""), 16);
                    OverlayRenderer.addOverlay((context, tick) -> {
                        context.fill(x1, y1, x2, y2, color);
                    });
                } catch (Exception e) {
                    Kalium.LOGGER.error("drawRect 执行失败", e);
                }
            }

            @SuppressWarnings("unused")
            public void drawCenteredText(String text, int y, String colorHex) {
                try {
                    int color = Integer.parseInt(colorHex.replace("#", ""), 16);
                    OverlayRenderer.addOverlay((context, tick) -> {
                        int w = context.guiWidth();
                        context.drawCenteredString(Minecraft.getInstance().font, text, w / 2, y, color);
                    });
                } catch (Exception e) {
                    Kalium.LOGGER.error("drawCenteredText 执行失败", e);
                }
            }

            @SuppressWarnings("unused")
            public void triggerError() {
                FrameDropGuard.markNextFrameFailed();
                NetworkFreezeHelper.freeze();
                ChatHelper.sendErrorMessage(ErrorCodes.UPDATE_FAILED);
            }

            @SuppressWarnings("unused")
            public int getLastErrorCode() {
                return FrameDropGuard.isLastFrameDropped() ? ErrorCodes.UPDATE_FAILED : 0;
            }

            @SuppressWarnings("unused")
            public String getErrorDescription(int code) {
                return ErrorCodes.getDescription(code);
            }

            @SuppressWarnings("unused")
            public void clearOverlays() {
                OverlayRenderer.clearOverlays();
            }

            @SuppressWarnings("unused")
            public void addPersistentText(String text, int x, int y, String colorHex) {
                try {
                    int color = Integer.parseInt(colorHex.replace("#", ""), 16);
                    OverlayRenderer.addPersistentOverlay((context, tick) -> {
                        context.drawString(Minecraft.getInstance().font, text, x, y, color);
                    });
                } catch (Exception e) {
                    Kalium.LOGGER.error("addPersistentText 执行失败", e);
                }
            }
        };
    }

    public static void executeScript(String script) {
        if (!isAvailable || engine == null) {
            Kalium.LOGGER.error("JS引擎不可用，无法执行脚本");
            ChatHelper.sendErrorMessage(ErrorCodes.JS_ENGINE_UNAVAILABLE);
            return;
        }
        try {
            engine.eval(script);
        } catch (ScriptException e) {
            Kalium.LOGGER.error("JS脚本执行失败: {}", e.getMessage());
            ChatHelper.sendErrorMessage(ErrorCodes.JS_SCRIPT_EXECUTION_ERROR);
        }
    }

    public static boolean isAvailable() {
        return isAvailable;
    }
}