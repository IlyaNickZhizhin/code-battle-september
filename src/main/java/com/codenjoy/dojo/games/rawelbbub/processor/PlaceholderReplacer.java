package com.codenjoy.dojo.games.rawelbbub.processor;

import com.codenjoy.dojo.games.rawelbbub.model.Board;

@FunctionalInterface
public interface PlaceholderReplacer {
    String prepare(Board board);
}
