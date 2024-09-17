package com.codenjoy.dojo.games.rawelbbub.processor;

import com.codenjoy.dojo.games.rawelbbub.builder.PromptBuilder;
import com.codenjoy.dojo.games.rawelbbub.model.Board;
import com.codenjoy.dojo.games.rawelbbub.processor.impl.PreviousTurnsReplacer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static com.codenjoy.dojo.games.rawelbbub.config.ConfigFileParser.MAX_TOKENS_OF_ANSWER;
import static com.codenjoy.dojo.games.rawelbbub.config.ConfigFileParser.STEPS_FOR_REFRESH_RULES;
import static com.codenjoy.dojo.games.rawelbbub.config.ConfigFileParser.TEMPERATURE;

public class PrompterProcessor {
    private static int counter;

    public PrompterProcessor() {
        counter = STEPS_FOR_REFRESH_RULES;
    }
    
    public Object getBody(Board board, int kd) {
        counter++;
        return Map.of(
                "model", "second-state/Mistral-Nemo-Instruct-2407-GGUF",
                "max_tokens", MAX_TOKENS_OF_ANSWER,
                "messages", List.of(
                        Map.of("role", "system",
                                "content", getSystemPrompt(board)),
                        Map.of("role", "user", "content", getUserPrompt(board, kd))
                ),
                "temperature", TEMPERATURE);
    }

    public String getSystemPrompt(Board board){
        try {
            if (counter<STEPS_FOR_REFRESH_RULES) {
                return Files.readString(Path.of(ClassLoader.getSystemResource("prompts/LONG_SYSTEM_PROMPT").toURI()));
            } else {
                return Files.readString(Path.of(ClassLoader.getSystemResource("prompts/SHORT_SYSTEM_PROMPT").toURI()));
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserPrompt(Board board, int kd) {
        try {
            if (counter<STEPS_FOR_REFRESH_RULES) {
                return prepareShortUserPrompt(board, kd);
            } else {
                counter = 0;
                return prepareLongUserPrompt(board, kd);
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String prepareLongUserPrompt(Board board, int kd) throws URISyntaxException, IOException {
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

    private String prepareShortUserPrompt(Board board, int kd) throws URISyntaxException, IOException {
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
