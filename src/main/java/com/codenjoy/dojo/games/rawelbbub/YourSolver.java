package com.codenjoy.dojo.games.rawelbbub;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.games.rawelbbub.handler.PromptHandler;
import com.codenjoy.dojo.games.rawelbbub.model.Action;
import com.codenjoy.dojo.games.rawelbbub.model.Board;
import com.codenjoy.dojo.games.rawelbbub.model.Turn;
import com.codenjoy.dojo.games.rawelbbub.processor.PrompterProcessor;
import com.codenjoy.dojo.games.rawelbbub.repository.Repository;
import com.codenjoy.dojo.services.Dice;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Author: your name
 *
 * This is your AI algorithm for the game.
 * Implement it at your own discretion.
 * Pay attention to {@link YourSolverTest} - there is
 * a test framework for you.
 */
public class YourSolver implements Solver<Board> {

    private Dice dice;
    private Board board;
    private PromptHandler promptHandler;
    private PrompterProcessor prompter;
    private Repository repository;
    private Gson gson;
    private int kd = 0;

    public YourSolver(Dice dice) {
        repository = Repository.getInstance();
        promptHandler = new PromptHandler();
        prompter = new PrompterProcessor();
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    @Override
    public String get(Board board) {
        this.board = board;

        if (board.isGameOver() && gameOverDoubleCheck()) {
            return "";
        }
        String response = promptHandler.gerTurn(board, kd);
        if (response.contains("ACT")) {
            kd = 4;
        } else {
            if (kd > 0) {
                kd--;
            }
        }
        repository.saveTurn(new Turn(board, new Action(response)));
        return response;
    }

    private boolean gameOverDoubleCheck() {
        // TODO find another answer on question is game over,
        //  except hero is null (it isn't always mean that the game is over)
        return false;
    }
}