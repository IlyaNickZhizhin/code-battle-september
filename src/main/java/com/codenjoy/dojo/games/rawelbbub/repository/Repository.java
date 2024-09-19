package com.codenjoy.dojo.games.rawelbbub.repository;

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
import java.util.HashMap;
import java.util.Map;

public class Repository {
    private final Map<Integer, Turn> turnStorage = new HashMap<>();
    private final String FILE_PATH = "repository/turns.ser";
    private final String TOTAL_TOKENS_FILE_PATH = "repository/tokens.txt";

    private static class SingletonHelper {
        private static final Repository INSTANCE;
        static {
            try {
                INSTANCE = new Repository();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Repository getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private Repository() throws URISyntaxException {
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
        Path repo = null;
        Path tokens = null;
        try {
            repo = Path.of(ClassLoader.getSystemResource(FILE_PATH).toURI());
            tokens = Path.of(ClassLoader.getSystemResource(TOTAL_TOKENS_FILE_PATH).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try {
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
                String data = "<Team>_<PlayerName>_<Round>=" + totalTokens;
                dos.writeBytes(data);
                System.out.println("Saving tokens sum value = " + totalTokens + " to: " + fileTokens.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadRepository() {
        File file = new File(String.valueOf(FILE_PATH));
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
}
