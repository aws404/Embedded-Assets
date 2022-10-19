package net.fabricmc.example.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class EmbeddedAssetsConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.PRIVATE).create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("EmbeddedAssetsLoader.json");

    public static List<String> priority = new ArrayList<>();

    public static void read() {
        if (!Files.exists(PATH)) {
            save();
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(PATH)) {
            GSON.fromJson(reader, EmbeddedAssetsConfig.class);
        } catch (IOException e) {
            System.out.println("Failed to read config, priorities unavailable until config is fixed:");
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            Files.write(PATH, GSON.toJson(EmbeddedAssetsConfig.class.newInstance()).getBytes());
        } catch (Exception e) {
            System.out.println("Failed to save config:");
            e.printStackTrace();
        }
    }

}
