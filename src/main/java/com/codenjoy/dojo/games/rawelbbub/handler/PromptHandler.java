package com.codenjoy.dojo.games.rawelbbub.handler;

import com.codenjoy.dojo.games.rawelbbub.PrompterProcessor;
import com.codenjoy.dojo.games.rawelbbub.gateway.AIGateway;
import com.codenjoy.dojo.games.rawelbbub.gateway.MockGateway;
import com.codenjoy.dojo.games.rawelbbub.model.Board;
import com.codenjoy.dojo.games.rawelbbub.repository.TurnsRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import feign.RetryableException;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.codenjoy.dojo.games.rawelbbub.config.ConfigFileParser.MODEL_NAME;
import static com.codenjoy.dojo.games.rawelbbub.config.ConfigFileParser.MODEL_VERSION;
import static com.codenjoy.dojo.games.rawelbbub.config.ConfigFileParser.TOKEN;

public class PromptHandler {

    private final AIGateway gateway;
    private final String[] model;
    Map<String, String> headers;
    private final Gson gson;
    private final PrompterProcessor prompter;
    private final TurnsRepository repository;

    public PromptHandler() {
/*        gateway = Feign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .target(AIGateway.class, "https://ai-proxy.lab.epam.com");*/
        gateway = new MockGateway();
        headers = Map.of(
                "API-KEY", TOKEN,
                "Content-Type", "application/json");
        model = new String[]{MODEL_NAME, MODEL_VERSION};
        gson = new GsonBuilder().disableHtmlEscaping().create();
        prompter = new PrompterProcessor();
        repository = TurnsRepository.getInstance();
    }


    public String getTurn(Board board, int kd){
        String response;
        if (TOKEN.equals("keyboard")){
            return getTurnFromKeyboard();
        }
        if (TOKEN.isEmpty()) return getRandomTurn(board.toString(), kd);
        try {
            response = getTurnFromAI(prompter.getBody(board, kd));
        } catch (RetryableException e) {
            System.out.println(e.getMessage());
            response = getRandomTurn(board.toString(), kd);
        }
        return response;
    }

    private String getTurnFromKeyboard() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        switch (input.toUpperCase()) {
            case "W":
                return "UP";
            case "S":
                return "DOWN";
            case "A":
                return "LEFT";
            case "D":
                return "RIGHT";
            case " ":
                return "ACT";
            default:
                return "INVALID INPUT";
        }
    }

    private String getTurnFromAI(Object body){
        Object answer = gateway.getNextMove(model[0], model[1], headers, body);
        Map<Object,Object> choices = (Map<Object, Object>) ((List<Object>) ((Map<Object, Object>) answer).get("choices")).get(0);
        String response = ((Map<Object, Object>) choices.get("message")).get("content").toString();
        System.out.println("Answer from llm:");
        System.out.println(gson.toJson(answer));
        Map<Object,Object> usage = (Map<Object, Object>) ((Map<Object, Object>) answer).get("usage");
        double tokens = (Double) usage.get("total_tokens");
        repository.getTurn(repository.getMaxTurnNumber()).setTokens(tokens);
        return response;
    }

    private String getRandomTurn(String string, int coolDown) {
        System.out.println("WARNING!!!! Check TOKEN (or other causes), and your code, seemly AI was not connected, turns create randomly");
        StringBuilder response = new StringBuilder();
        if (coolDown == 0) {
            response.append("ACT");
        }
        int random = (int) (Math.random()*4);
        switch (random) {
            case 0: return response.append(" UP").toString();
            case 1: return response.append(" DOWN").toString();
            case 2: return response.append(" LEFT").toString();
            case 3: return response.append(" RIGHT").toString();
            case 4: return response.toString();
            default: return response.toString();
        }
    }
}
