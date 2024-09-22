package com.codenjoy.dojo.games.rawelbbub.replacers;

import com.codenjoy.dojo.games.rawelbbub.model.Board;

@FunctionalInterface
public interface PlaceholderReplacer {
    String prepare(Board board);
}
