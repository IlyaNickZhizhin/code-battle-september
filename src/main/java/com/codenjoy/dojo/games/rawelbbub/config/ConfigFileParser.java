package com.codenjoy.dojo.games.rawelbbub.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ConfigFileParser {
    public static String TEAM_NAME;
    public static String PLAYER_NAME;
    public static String TOKEN;
    public static String MODEL_NAME;
    public static String MODEL_VERSION;
    public static int COOL_DOWN;
    public static int TURNS_FOR_STORE;
    public static int STEPS_FOR_REFRESH_RULES;
    public static double TEMPERATURE;
    public static int MAX_TOKENS_OF_ANSWER;

    static {
        int  maxTokensOfAnswer =400;
        double temperature=0.3;
        int stepsForRefreshRules = 5;
        int turnsForStore = 2;
        String modelName = "gpt-35-turbo-16k";
        String modelVersion = "2024-02-01";
        try {
            Map<String, String> config = readConfig();
            TEAM_NAME=config.getOrDefault("teamName", "UNNAMED_TEAM");
            PLAYER_NAME=config.getOrDefault("playerName", getRandomNAme(10));
            TOKEN = config.getOrDefault("token", "secret");
            MODEL_NAME = config.getOrDefault("model_name", modelName);
            MODEL_VERSION = config.getOrDefault("model_version", modelVersion);
            COOL_DOWN = Integer.parseInt(config.getOrDefault("coolDown", "4"));
            MAX_TOKENS_OF_ANSWER = Integer.parseInt(config.getOrDefault("maxTokensOfAnswer", String.valueOf(maxTokensOfAnswer)));
            TEMPERATURE = Double.parseDouble(config.getOrDefault("temperature", String.valueOf(temperature)));
            STEPS_FOR_REFRESH_RULES = Integer.parseInt(config.getOrDefault("stepsForRefreshRules", String.valueOf(stepsForRefreshRules)));
            TURNS_FOR_STORE = Integer.parseInt(config.getOrDefault("turnsForStore", String.valueOf(turnsForStore)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, String> readConfig() throws IOException, URISyntaxException {
        Map<String, String> config = new HashMap<>();
        Path path = Path.of(ClassLoader.getSystemResource("config.yaml").toURI());
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    config.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
        return config;
    }

    private static String getRandomNAme(int length){
        String alphabet = " ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder name = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            name.append(alphabet.charAt(index));
        }

        return name.toString();
    }
}
