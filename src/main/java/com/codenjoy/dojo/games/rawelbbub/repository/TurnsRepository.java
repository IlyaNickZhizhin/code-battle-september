package com.codenjoy.dojo.games.rawelbbub.repository;

import com.codenjoy.dojo.games.rawelbbub.model.Turn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.codenjoy.dojo.games.rawelbbub.config.ConfigFileParser.PLAYER_NAME;
import static com.codenjoy.dojo.games.rawelbbub.config.ConfigFileParser.TEAM_NAME;

public class TurnsRepository {
    private final Map<Integer, Turn> turnStorage = new HashMap<>();

    private static class SingletonHelper {
        private static final TurnsRepository INSTANCE;
        static {
            try {
                INSTANCE = new TurnsRepository();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static TurnsRepository getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private TurnsRepository() throws URISyntaxException {
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveRepository));
    }

    public void saveTurn(Turn turn) {
        turnStorage.put(turn.getNumber(), turn);
    }

    public Turn getLast(){
        return turnStorage.get(turnStorage.size());
    }

    public Turn getTurn(int number) {
        return turnStorage.get(number);
    }

    private void saveRepository() {
        try {
            Path tokens = getProjectRoot().resolve("logs/tokens.txt");
            File fileTokens = new File(String.valueOf(tokens));
            fileTokens.getParentFile().mkdirs();
            double totalTokens = turnStorage.values().stream().mapToDouble(Turn::getTokens).sum();
            try (FileWriter fw = new FileWriter(fileTokens, true); // true для добавления в конец файла
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                String data =TEAM_NAME + "_" + PLAYER_NAME + "_" + Instant.now().getEpochSecond() + "=" + totalTokens;
                out.println(data);
                System.out.println("Saving tokens sum value = " + totalTokens + " to: " + fileTokens.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMaxTurnNumber() {
        if (turnStorage.isEmpty()) {return 0;}
        return turnStorage.keySet().stream().max(Integer::compare).orElse(0);
    }

    private Path getProjectRoot() {
        try {
            return Path.of(new File(
                    Thread.currentThread().getContextClassLoader().getResource("config.yaml").toURI()
                            .resolve("..")
                            .resolve("..")
            ).getPath());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
