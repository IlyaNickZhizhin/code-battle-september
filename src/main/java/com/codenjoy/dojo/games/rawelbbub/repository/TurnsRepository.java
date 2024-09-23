package com.codenjoy.dojo.games.rawelbbub.repository;

import com.codenjoy.dojo.games.rawelbbub.config.ConfigFileParser;
import com.codenjoy.dojo.games.rawelbbub.model.Turn;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.codenjoy.dojo.games.rawelbbub.config.ConfigFileParser.PLAYER_NAME;
import static com.codenjoy.dojo.games.rawelbbub.config.ConfigFileParser.ROOT_FOLDER_NAME;
import static com.codenjoy.dojo.games.rawelbbub.config.ConfigFileParser.TEAM_NAME;

public class TurnsRepository {
    private final Map<Integer, Turn> turnStorage = new HashMap<>();
    private final String FILE_PATH = "logs/turns.ser";
    private final String TOTAL_TOKENS_FILE_PATH = "logs/tokens.txt";

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
        loadRepository();
        Turn.initializeCounter(getMaxTurnNumber());
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveRepository));
    }

    public Turn saveTurn(Turn turn) {
        turnStorage.put(turn.getNumber(), turn);
        return turn;
    }

    public Turn getLast(){
        return turnStorage.get(turnStorage.size() - 1);
    }

    public Turn getTurn(int number) {
        return turnStorage.get(number);
    }

    private void saveRepository(){
        try {
            Path repo = getProjectRoot().resolve(FILE_PATH);
            Path tokens = getProjectRoot().resolve(TOTAL_TOKENS_FILE_PATH);
            File fileTurns = new File(String.valueOf(repo));
            fileTurns.getParentFile().mkdirs();
            File fileTokens = new File(String.valueOf(tokens));
            fileTokens.getParentFile().mkdirs();
            int totalTokens = turnStorage.values().stream().mapToInt(Turn::getTokens).sum();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileTurns))) {
                oos.writeObject(turnStorage);
                System.out.println("Saving file to: " + fileTurns.getAbsolutePath());
            }
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileTokens))) {
                String data = TEAM_NAME + "_" + PLAYER_NAME + "_" + Instant.now().getEpochSecond() + "=" + totalTokens;
                dos.writeBytes(data);
                System.out.println("Saving tokens sum value = " + totalTokens + " to: " + fileTokens.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadRepository() {
        Path repo = getProjectRoot().resolve(FILE_PATH);
        File file = new File(String.valueOf(repo));
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Map<Integer, Turn> loadedTurns = (Map<Integer, Turn>) ois.readObject();
                turnStorage.putAll(loadedTurns);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            turnStorage.clear();
        }
    }

    public int getMaxTurnNumber() {
        return turnStorage.keySet().stream().max(Integer::compare).orElse(0);
    }

    private Path getProjectRoot() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        while (currentPath != null && !currentPath.endsWith(ROOT_FOLDER_NAME)) {
            currentPath = currentPath.getParent();
        }
        if (currentPath == null) {
            throw new RuntimeException("Project root '" + ROOT_FOLDER_NAME + "' not found. check config file");
        }
        return currentPath;
    }
}
