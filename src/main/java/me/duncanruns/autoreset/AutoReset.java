package me.duncanruns.autoreset;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Scanner;

public class AutoReset implements ModInitializer {
    public static final String MOD_ID = "autoreset";
    public static final String MOD_NAME = "Auto Reset Mod";
    public static final long DEFAULT_SEED = -3294725893620991126L;
    public static long seed = DEFAULT_SEED;
    public static boolean isPlaying = false;
    public static Logger LOGGER = LogManager.getLogger();


    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

    public static int getNextAttempt() {
        try {
            File file = new File("attempts.txt");
            int value;
            if (file.exists()) {
                Scanner fileReader = new Scanner(file);
                String string = fileReader.nextLine().trim();
                fileReader.close();
                try {
                    value = Integer.parseInt(string);
                } catch (NumberFormatException ignored) {
                    value = 0;
                }
            } else {
                value = 0;
            }
            value++;
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(Integer.toString(value));
            fileWriter.close();
            return value;
        } catch (IOException ignored) {
            return -1;
        }
    }

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing AutoReset (SS version)");
        seed = readSeed();
    }

    public static long readSeed() {
        try {
            File seedFile = new File("seed.txt");
            boolean existed = !seedFile.createNewFile();
            if (existed)
            {
                Scanner scanner = new Scanner(seedFile);
                if(scanner.hasNextLong())
                {
                    long res = scanner.nextLong();
                    scanner.close();
                    return res;
                }
                else
                {
                    log(Level.ERROR, "No seed found in seed.txt");
                    return DEFAULT_SEED;
                }
            }
            else
            {
                PrintWriter f = new PrintWriter(seedFile);
                f.println(DEFAULT_SEED);
                f.close();
                return DEFAULT_SEED;
            }
        }
        catch (IOException e)
        {
            log(Level.ERROR, e.toString());
            return DEFAULT_SEED;
        }
    }
}