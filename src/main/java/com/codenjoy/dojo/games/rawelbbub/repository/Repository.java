package com.codenjoy.dojo.games.rawelbbub.repository;

import com.codenjoy.dojo.games.rawelbbub.model.Turn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Repository {
    private final Map<Integer, Turn> turnStorage = new HashMap<>();
    private final String FILE_PATH = "java/src/main/resources/turns.ser";

    private static class SingletonHelper {
        private static final Repository INSTANCE = new Repository();
    }

    public static Repository getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private Repository() {
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

    private void saveRepository() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(turnStorage);
                System.out.println("Saving file to: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadRepository() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
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
