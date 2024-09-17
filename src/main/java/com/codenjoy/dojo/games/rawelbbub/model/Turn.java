package com.codenjoy.dojo.games.rawelbbub.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Turn implements Serializable {
    private static final long serialVersionUID = 1L;
    private static volatile AtomicInteger counter;

    private final int number;
    private transient final Board board;
    private final Action action;
    private final String boardAsString;

    public Turn(Board board, Action action) {
        this.board = board;
        this.action = action;
        this.boardAsString = board.toString();
        this.number = getCounter().incrementAndGet();
    }

    private static AtomicInteger getCounter() {
        if (counter == null) {
            synchronized (Turn.class) {
                if (counter == null) {
                    counter = new AtomicInteger(0);
                }
            }
        }
        return counter;
    }

    public static void initializeCounter(int initialValue) {
        counter = new AtomicInteger(initialValue);
    }

    public int getNumber() {
        return number;
    }

    public Board getBoard() {
        return board;
    }

    public Action getAction() {
        return action;
    }
}
