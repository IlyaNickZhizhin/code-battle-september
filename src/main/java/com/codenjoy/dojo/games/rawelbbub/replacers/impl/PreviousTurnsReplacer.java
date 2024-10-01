package com.codenjoy.dojo.games.rawelbbub.replacers.impl;

import com.codenjoy.dojo.games.rawelbbub.model.Board;
import com.codenjoy.dojo.games.rawelbbub.model.Turn;
import com.codenjoy.dojo.games.rawelbbub.replacers.RepositoryPlaceholderReplacer;

import static com.codenjoy.dojo.games.rawelbbub.config.ConfigFileParser.TURNS_FOR_STORE;

public class PreviousTurnsReplacer implements RepositoryPlaceholderReplacer {

    @Override
    public String prepareFromRepository() {
        if (isTurnAvailable(repository.getLast())) return "NO PREVIOUS TURNS";
        int maxTurnStored = repository.getMaxTurnNumber();
        if (TURNS_FOR_STORE<maxTurnStored) {
            return getPreviousNeededTurns(TURNS_FOR_STORE);
        } else return getPreviousNeededTurns(repository.getMaxTurnNumber());
    }

    @Override
    public String prepare(Board board) {
        return RepositoryPlaceholderReplacer.super.prepare(board);
    }


    @Override
    public String prepare() {
        return RepositoryPlaceholderReplacer.super.prepare();
    }

    private String getPreviousNeededTurns(int numberOfNeededTurns){
        StringBuilder previousTurnsString = new StringBuilder();
        for (int i = 0; i < numberOfNeededTurns; i++) {
            String turnName;
            previousTurnsString.append("\n");
            Turn turn;
            if (i==0) {
                turn = repository.getLast();
                if (isTurnAvailable(turn)) {
                    previousTurnsString.append("Last turn: ").append(turn.getBoard().getHero()).append("\n");
                    previousTurnsString.append("Your last action was: ").append(turn.getAction().toShortString()).append("\n");
                }
            } else  {
                turn = repository.getTurn(repository.getMaxTurnNumber()-i);
                if (isTurnAvailable(turn)) {
                    turnName = "turn # -"+(i+1) + " ";
                    previousTurnsString.append(turnName).append(repository.getTurn(repository.getMaxTurnNumber()-i).getBoard().getHero()).append("\n");
                    previousTurnsString.append("Your action on ").append(turnName).append("was: ").append((repository.getTurn(repository.getMaxTurnNumber()-i).getAction().toShortString())).append("\n");
                }
            }
        }
        return previousTurnsString.toString();
    }

    private boolean isTurnAvailable(Turn turn){
        return turn!=null && turn.getBoard()!=null;
    }
}
