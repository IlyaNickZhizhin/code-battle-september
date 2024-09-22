package com.codenjoy.dojo.games.rawelbbub.builder;

import com.codenjoy.dojo.games.rawelbbub.model.Board;
import com.codenjoy.dojo.games.rawelbbub.replacers.BoardPlaceholderReplacer;
import com.codenjoy.dojo.games.rawelbbub.replacers.PlaceholderReplacer;
import com.codenjoy.dojo.games.rawelbbub.replacers.RepositoryPlaceholderReplacer;

import java.util.ArrayList;
import java.util.List;

public class PromptBuilder {

    private String template;

    private final List<ReplacementPair> processors = new ArrayList<>();

    public PromptBuilder(String template) {
        this.template = template;
    }

    public PromptBuilder append(String placeholder, PlaceholderReplacer preparatory) {
        processors.add(new ReplacementPair(placeholder, preparatory));
        return this;
    }

    public PromptBuilder append(String placeholder, String replacement) {
        processors.add(new ReplacementPair(placeholder, replacement));
        return this;
    }

    public String build(Board board) {
        for (ReplacementPair processor : processors) {
            if (processor.preparer != null) {
                if (processor.preparer instanceof BoardPlaceholderReplacer) {
                    BoardPlaceholderReplacer preparer = (BoardPlaceholderReplacer) processor.preparer;
                    template = template.replace(processor.placeholder, preparer.prepare(board));
                }
                if (processor.preparer instanceof RepositoryPlaceholderReplacer) {
                    RepositoryPlaceholderReplacer preparer = (RepositoryPlaceholderReplacer) processor.preparer;
                    template = template.replace(processor.placeholder, preparer.prepare());
                }
            } else {
                template = template.replace(processor.placeholder, processor.replacement);
            }
        }
        return template;
    }

    static class ReplacementPair {
        String placeholder;
        PlaceholderReplacer preparer;
        String replacement;

        public ReplacementPair(String placeholder, PlaceholderReplacer preparer) {
            this.placeholder = placeholder;
            this.preparer = preparer;
            this.replacement = null;
        }

        public ReplacementPair(String placeholder, String replacement) {
            this.placeholder = placeholder;
            this.preparer = null;
            this.replacement = replacement;
        }
    }
}
