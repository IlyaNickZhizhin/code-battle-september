package com.codenjoy.dojo.games.rawelbbub.gateway;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockGateway implements AIGateway {
    @Override
    public Object getNextMove(String deploymentName, String apiVersion, Object headers, Object body) {
        return createMockResponse();
    }

    @Override
    public Object getNextMoveLocal(Object headers, Object body) {
        return createMockResponse();
    }

    private Object createMockResponse() {
        Map<String, Object> message = new HashMap<>();
        message.put("content", "UP");

        Map<String, Object> choice = new HashMap<>();
        choice.put("message", message);

        Map<String, Object> usage = new HashMap<>();
        usage.put("total_tokens", 10.0);

        Map<String, Object> response = new HashMap<>();
        response.put("choices", List.of(choice));
        response.put("usage", usage);

        return response;
    }
}
