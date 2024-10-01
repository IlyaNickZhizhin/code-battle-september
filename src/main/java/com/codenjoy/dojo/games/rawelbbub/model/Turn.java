package com.codenjoy.dojo.games.rawelbbub.model;

public class Turn{
    private static int counter;
    private final int number;
    private transient final Board board;
    private Action action;
    private double tokens;

    public Turn(Board board) {
        this.board = board;
        this.number = getCounter();
        this.tokens = 0;
    }

    private static int getCounter() {
        return ++counter;
    }

    public void setTokens(double tokens) {
        this.tokens = tokens;
    }

    public double getTokens() {
        return tokens;
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

    public void setAction(Action action) {
        this.action = action;
    }

}
