package org.kalium.helper;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ServerboundKeepAlivePacket;  // ← 修正导入
import net.minecraft.network.protocol.game.*;
import java.util.concurrent.atomic.AtomicReference;

public class NetworkFreezeHelper {
    private static final AtomicReference<FreezeState> state = new AtomicReference<>(FreezeState.NORMAL);

    public static void freeze() {
        state.set(FreezeState.FROZEN);
    }

    public static void unfreeze() {
        state.set(FreezeState.NORMAL);
    }

    public static void forceUnfreeze() {
        state.set(FreezeState.NORMAL);
    }

    public static boolean isFrozen() {
        return state.get() == FreezeState.FROZEN;
    }

    public static boolean shouldBlockPacket(Packet<?> packet) {
        if (!isFrozen()) return false;

        // 保留心跳包，防止被踢
        if (packet instanceof ServerboundKeepAlivePacket) {  // ← 现在能正确识别了
            return false;
        }

        // 拦截所有移动、交互、使用物品等行为包
        if (packet instanceof ServerboundMovePlayerPacket ||
            packet instanceof ServerboundUseItemPacket ||
            packet instanceof ServerboundInteractPacket ||
            packet instanceof ServerboundPlayerActionPacket ||
            packet instanceof ServerboundSetCarriedItemPacket) {
            return true;
        }
        return false;
    }

    public static FreezeState getState() {
        return state.get();
    }
}