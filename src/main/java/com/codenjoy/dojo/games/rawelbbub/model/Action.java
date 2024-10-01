package com.codenjoy.dojo.games.rawelbbub.model;

import com.codenjoy.dojo.services.Direction;

public class Action {
    private final boolean wasAct;
    private final Direction move;
    private final String description;

    public Action(String response) {
        this.wasAct = response.contains("ACT");
        this.move = parseMove(response);
        this.description = parseDescription(response);
    }

    private Direction parseMove(String response) {
        String[] parts = response.split(" ");
        for (String part : parts) {
            switch (part) {
                case "UP":
                    return Direction.UP;
                case "DOWN":
                    return Direction.DOWN;
                case "LEFT":
                    return Direction.LEFT;
                case "RIGHT":
                    return Direction.RIGHT;
            }
        }
        return Direction.STOP;
    }

    private String parseDescription(String response) {
        int colonIndex = response.indexOf(":");
        if (colonIndex != -1) {
            return response.substring(colonIndex + 1).trim();
        } else {
            return "";
        }
    }

    public String toShortString() {
        return (wasAct ? "ACT " : "")+move;
    }

    @Override
    public String toString() {
        return (wasAct ? "ACT " : "")+move + " : " + description;
    }
}
