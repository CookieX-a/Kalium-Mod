package org.kalium.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.Packet;
import org.kalium.helper.NetworkFreezeHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Inject(at = @At("HEAD"), method = "send", cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        if (NetworkFreezeHelper.shouldBlockPacket(packet)) {
            // 拦截该包，不发送
            ci.cancel();
        }
    }
}