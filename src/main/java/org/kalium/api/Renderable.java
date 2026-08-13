package org.kalium.api;

import net.minecraft.client.gui.GuiGraphics;

@FunctionalInterface
public interface Renderable {
    void render(GuiGraphics context, float tickDelta);
}