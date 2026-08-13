package org.kalium.helper;

import net.minecraft.network.protocol.Packet;
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

        if (packet instanceof ServerboundKeepAlivePacket ||
            packet instanceof ServerboundPongPacket) {
            return false;
        }

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