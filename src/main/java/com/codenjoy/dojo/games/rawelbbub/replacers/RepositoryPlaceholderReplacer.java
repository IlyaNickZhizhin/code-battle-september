package com.codenjoy.dojo.games.rawelbbub.replacers;

import com.codenjoy.dojo.games.rawelbbub.model.Board;
import com.codenjoy.dojo.games.rawelbbub.repository.TurnsRepository;

@FunctionalInterface
public interface RepositoryPlaceholderReplacer extends PlaceholderReplacer {

    TurnsRepository repository = TurnsRepository.getInstance();

    @Override
    default String prepare(Board board) {
        return prepareFromRepository();
    }

    String prepareFromRepository();

    default String prepare() {
        return prepareFromRepository();
    }

}
