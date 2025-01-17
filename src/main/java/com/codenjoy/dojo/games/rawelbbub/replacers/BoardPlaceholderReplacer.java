package com.codenjoy.dojo.games.rawelbbub.replacers;

import com.codenjoy.dojo.games.rawelbbub.model.Board;

public interface BoardPlaceholderReplacer extends PlaceholderReplacer {

    @Override
    default String prepare(Board board) {
        return prepareFromBoard(board);
    }

    String prepareFromBoard(Board board);
}
