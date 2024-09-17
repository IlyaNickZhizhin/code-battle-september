package com.codenjoy.dojo.games.rawelbbub;

import com.codenjoy.dojo.games.rawelbbub.builder.PromptBuilder;
import com.codenjoy.dojo.games.rawelbbub.model.Board;
import com.codenjoy.dojo.games.rawelbbub.processor.BoardPlaceholderReplacer;
import com.codenjoy.dojo.games.rawelbbub.processor.RepositoryPlaceholderReplacer;
import com.codenjoy.dojo.games.rawelbbub.processor.impl.PreviousTurnsReplacer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Service {

    public static String prepareLongUserPrompt(Board board, int kd) throws URISyntaxException, IOException {
        String template = Files.readString(Path.of(ClassLoader.getSystemResource("prompts/LONG_USER_PROMPT").toURI()));
        return new PromptBuilder(template)
                .append("PLACE_FOR_BOARD", "\n"+board.toString())
                .append("PLACE_FOR_PREVIOUS_TURNS", new PreviousTurnsReplacer())
                .append("TURNS_FOR_ACT", kd==0 ? "AVAILABLE":"DONT SHOOT, it will be available in " + kd + "turns")
                .append("YOUR_CUSTOM_PLACEHOLDER_WITH_INFO_FROM_BOARD",
                        (BoardPlaceholderReplacer) (brd) -> "IMPLEMENT YOUR LOGIC HERE")
                .append("YOUR_CUSTOM_PLACEHOLDER_WITH_INFO_FROM_REPOSITORY",
                        (RepositoryPlaceholderReplacer) ()-> "IMPLEMENT YOUR LOGIC HERE")
                .build(board);
    }

    private static String prepareShortUserPrompt(Board board, int kd) throws URISyntaxException, IOException {
        String currentBoardShort = "Hero at:" + board.toString().split("Hero at:")[1];
        String template = Files.readString(Path.of(ClassLoader.getSystemResource("prompts/SHORT_USER_PROMPT").toURI()));
        template = template.replace("PLACE_FOR_BOARD", currentBoardShort);
        template = template.replace("TURNS_FOR_ACT", kd==0 ? "now" : "in " + kd + " turns "+ (kd==0 ? "AVAILABLE":"NOT AVAILABLE, DONT SHOOT"));
        return new PromptBuilder(template)
                .append("PLACE_FOR_BOARD", "Hero at:" + board.getHero() + "\n" +
                        "Enemies at: " + board.getEnemies() + "\n" + "Torpedoes at: " + board.getTorpedoes())
                .append("TURNS_FOR_ACT", kd==0 ? "AVAILABLE":"DONT SHOOT, it will be available in " + kd + "turns")
                .build(board);
    }

}
