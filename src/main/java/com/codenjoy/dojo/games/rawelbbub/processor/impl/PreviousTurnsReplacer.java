package com.codenjoy.dojo.games.rawelbbub.processor.impl;

import com.codenjoy.dojo.games.rawelbbub.model.Board;
import com.codenjoy.dojo.games.rawelbbub.processor.RepositoryPlaceholderReplacer;

import static com.codenjoy.dojo.games.rawelbbub.config.ConfigFileParser.TURNS_FOR_STORE;

public class PreviousTurnsReplacer implements RepositoryPlaceholderReplacer {

    @Override
    public String prepareFromRepository() {
        StringBuilder previousTurnsString = new StringBuilder();
        for (int i = 0; i < TURNS_FOR_STORE; i++) {
            String turnName;
            previousTurnsString.append("\n");
            if (i==0) {
                previousTurnsString.append("Last turn: ").append(repository.getLast().getBoard().getHero()).append("\n");
                previousTurnsString.append("Your last action was: ").append(repository.getLast().getAction().toShortString()).append("\n");
            } else  {
                turnName = "turn # -"+(i+1) + " ";
                previousTurnsString.append(turnName).append(repository.getTurn(repository.getMaxTurnNumber()-i).getBoard().getHero()).append("\n");
                previousTurnsString.append("Your action on ").append(turnName).append("was: ").append((repository.getTurn(repository.getMaxTurnNumber()-i).getAction().toShortString())).append("\n");
            }
        }
        return previousTurnsString.toString();
    }

    @Override
    public String prepare(Board board) {
        return RepositoryPlaceholderReplacer.super.prepare(board);
    }


    @Override
    public String prepare() {
        return RepositoryPlaceholderReplacer.super.prepare();
    }
}
