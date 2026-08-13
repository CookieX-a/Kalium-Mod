package org.kalium;

import helper.ChatHelper;
import helper.NetworkFreezeHelper;

public class FrameDropGuard {
    private static long frameStartTime = 0;
    private static boolean lastFrameDropped = false;
    private static int dropCount = 0;
    private static final long DROP_THRESHOLD_MS = 500;
    private static final int MAX_DROPS = 5;

    public static void onRenderStart() {
        frameStartTime = System.currentTimeMillis();
    }

    public static boolean shouldDropFrame() {
        long elapsed = System.currentTimeMillis() - frameStartTime;
        if (elapsed > DROP_THRESHOLD_MS) {
            dropCount++;
            if (!lastFrameDropped) {
                lastFrameDropped = true;
                NetworkFreezeHelper.freeze();
                ChatHelper.sendErrorMessage(api.ErrorCodes.UPDATE_FAILED);
                Kalium.LOGGER.warn("渲染超时 {}ms，已丢弃本帧，冻结网络 (第{}次)", elapsed, dropCount);
            }

            if (dropCount >= MAX_DROPS) {
                Kalium.LOGGER.error("连续丢帧 {} 次，触发强制恢复！", MAX_DROPS);
                ChatHelper.sendErrorMessage(api.ErrorCodes.FRAME_DROP_RECOVERY_TIMEOUT);
                NetworkFreezeHelper.unfreeze();
                dropCount = 0;
                lastFrameDropped = false;
                return false;
            }
            return true;
        }

        if (lastFrameDropped) {
            lastFrameDropped = false;
            dropCount = 0;
            NetworkFreezeHelper.unfreeze();
            ChatHelper.resetMessageFlag();
            Kalium.LOGGER.info("渲染恢复，网络解冻");
        }
        return false;
    }

    public static void markNextFrameFailed() {
        lastFrameDropped = true;
        dropCount = 0;
    }

    public static boolean isLastFrameDropped() {
        return lastFrameDropped;
    }

    public static int getDropCount() {
        return dropCount;
    }
}