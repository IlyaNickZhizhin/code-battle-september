package com.codenjoy.dojo.games.rawelbbub.processor;

import com.codenjoy.dojo.games.rawelbbub.model.Board;
import com.codenjoy.dojo.games.rawelbbub.repository.Repository;

@FunctionalInterface
public interface RepositoryPlaceholderReplacer extends PlaceholderReplacer {

    Repository repository = Repository.getInstance();

    @Override
    default String prepare(Board board) {
        return prepareFromRepository();
    }

    String prepareFromRepository();

    default String prepare() {
        return prepareFromRepository();
    }

}
