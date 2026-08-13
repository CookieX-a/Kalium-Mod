package org.kalium.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.kalium.Kalium;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OverlayRenderer {
    private static final ConcurrentLinkedQueue<Renderable> overlayQueue = new ConcurrentLinkedQueue<>();
    private static final ConcurrentLinkedQueue<Renderable> persistentOverlays = new ConcurrentLinkedQueue<>();

    public static void addOverlay(Renderable renderable) {
        overlayQueue.add(renderable);
    }

    public static void addPersistentOverlay(Renderable renderable) {
        persistentOverlays.add(renderable);
    }

    public static void clearOverlays() {
        overlayQueue.clear();
    }

    public static void clearPersistentOverlays() {
        persistentOverlays.clear();
    }

    public static void renderAll(GuiGraphics context, float tickDelta) {
        if (Minecraft.getInstance().player == null) return;

        try {
            // 先渲染一次性覆盖
            for (Renderable task : overlayQueue) {
                try {
                    task.render(context, tickDelta);
                } catch (Exception e) {
                    Kalium.LOGGER.error("覆盖层渲染异常", e);
                }
            }
            overlayQueue.clear();

            // 再渲染持久覆盖
            for (Renderable task : persistentOverlays) {
                try {
                    task.render(context, tickDelta);
                } catch (Exception e) {
                    Kalium.LOGGER.error("持久覆盖层渲染异常", e);
                }
            }
        } catch (Exception e) {
            Kalium.LOGGER.error("OverlayRenderer整体渲染异常", e);
        }
    }
}