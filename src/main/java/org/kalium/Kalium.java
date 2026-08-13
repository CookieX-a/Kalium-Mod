package org.kalium;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Kalium implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "kalium";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Kalium v{} 服务端初始化完成！", "0.0.1-beta-SNAPSHOT");
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Kalium v{} 客户端初始化成功", "0.0.1-beta-SNAPSHOT");
        api.KaliumJSAPI.warmup();
    }
}