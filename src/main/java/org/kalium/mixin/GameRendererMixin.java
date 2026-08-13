package org.kalium.mixin;

import net.minecraft.client.renderer.GameRenderer;
import org.kalium.FrameDropGuard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(at = @At("HEAD"), method = "render")
    private void onRenderStart(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        FrameDropGuard.onRenderStart();
    }

    @Inject(at = @At("RETURN"), method = "render")
    private void onRenderEnd(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        // 渲染结束后检查是否丢帧，用于统计
        if (FrameDropGuard.isLastFrameDropped()) {
            // 可选：记录日志
        }
    }
}