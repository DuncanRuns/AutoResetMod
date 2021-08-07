package me.duncanruns.autoreset;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutoReset implements ModInitializer {
    public static final String MOD_ID = "autoreset";
    public static final String MOD_NAME = "Auto Reset Mod";
    public static boolean isPlaying = false;
    public static boolean loopPrevent = false;
    public static Logger LOGGER = LogManager.getLogger();

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
    }

}