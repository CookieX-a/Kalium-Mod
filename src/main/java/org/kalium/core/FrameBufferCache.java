package org.kalium.core;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import org.kalium.Kalium;

public class FrameBufferCache {
    private static RenderTarget cachedFrame = null;
    private static boolean hasCache = false;

    public static void captureCurrentFrame() {
        try {
            RenderTarget mainTarget = Minecraft.getInstance().getMainRenderTarget();
            if (mainTarget != null) {
                cachedFrame = mainTarget;
                hasCache = true;
            }
        } catch (Exception e) {
            Kalium.LOGGER.error("帧缓冲捕获失败", e);
        }
    }

    public static RenderTarget getCachedFrame() {
        return hasCache ? cachedFrame : null;
    }

    public static boolean hasCache() {
        return hasCache;
    }

    public static void clearCache() {
        cachedFrame = null;
        hasCache = false;
    }
}