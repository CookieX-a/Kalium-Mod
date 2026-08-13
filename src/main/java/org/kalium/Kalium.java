package org.kalium;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kalium.api.KaliumJSAPI;

public class Kalium implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "kalium";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final String VERSION = getVersion();

    private static String getVersion() {
        String version = Kalium.class.getPackage().getImplementationVersion();
        return version != null ? version : "DEV-BUILD";
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Kalium v{} 服务端初始化完成！", VERSION);
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Kalium v{} 客户端初始化完成！优化已开启", VERSION);
        KaliumJSAPI.warmup();
    }
}