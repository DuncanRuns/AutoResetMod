package me.duncanruns.autoreset;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AutoReset implements ModInitializer {
    public static final String MOD_ID = "autoreset";
    public static final String MOD_NAME = "Auto Reset Mod";
    public static boolean isPlaying = false;
    public static Logger LOGGER = LogManager.getLogger();
    public static int difficulty = 1;

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

    public static void saveDifficulty() {
        try {
            File file = new File("ardifficulty.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(String.valueOf(difficulty));
            fileWriter.close();
        } catch (Exception exception) {
            log(Level.ERROR, "Could not save difficulty for Auto Reset:\n" + exception.getMessage());
        }
    }

    public static void loadDifficulty() {
        try {
            File file = new File("ardifficulty.txt");
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            scanner.close();
            difficulty = Integer.parseInt(line.trim());
            if(difficulty > 4){
                difficulty = 1;
            }
        } catch (Exception exception) {
            log(Level.ERROR, "Could not load difficulty for Auto Reset:\n" + exception.getMessage());
        }
    }

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        File difficultyFile = new File("ardifficulty.txt");
        if (!difficultyFile.exists()) {
            saveDifficulty();
        } else {
            loadDifficulty();
        }
    }

}