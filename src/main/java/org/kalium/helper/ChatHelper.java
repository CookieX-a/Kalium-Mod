package org.kalium.helper;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.kalium.api.ErrorCodes;

public class ChatHelper {
    private static boolean messageSentThisDrop = false;

    public static void sendErrorMessage(int errorCode) {
        if (messageSentThisDrop) return;
        if (Minecraft.getInstance().player == null) return;

        String desc = ErrorCodes.getDescription(errorCode);
        Component msg = Component.literal("[KaliumHelper] ")
                .withStyle(ChatFormatting.RED)
                .append(Component.literal("渲染帧失败，Error Code: ")
                        .withStyle(ChatFormatting.WHITE))
                .append(Component.literal(String.valueOf(errorCode))
                        .withStyle(ChatFormatting.YELLOW))
                .append(Component.literal(" (" + desc + ")，已停止渲染本帧页面")
                        .withStyle(ChatFormatting.WHITE));

        Minecraft.getInstance().gui.getChat().addMessage(msg);
        messageSentThisDrop = true;
    }

    public static void resetMessageFlag() {
        messageSentThisDrop = false;
    }
}