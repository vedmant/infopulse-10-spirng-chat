package com.infopulse.mvc.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by vedmant on 2/25/17.
 */
@Service
// TODO implement
public class RedisService {

    public Jedis getJedis() {
        return null;
    }

    public List<ObjectNode> getAllMessages() {
        List<String> messages = getJedis().lrange("broadcast", 0, -1);

        ObjectMapper mapper = new ObjectMapper();

        List<ObjectNode> result = messages.stream().map(new Function<String, ObjectNode>() {
            @Override
            public ObjectNode apply(String message) {
                String[] messageParts = message.split(":");

                JsonNode senderNode = mapper.createObjectNode().path(messageParts[0]);
                JsonNode messageBodyNode = mapper.createObjectNode().path(messageParts[1]);

                ObjectNode messageNode = mapper.createObjectNode();
                messageNode.set("name", senderNode);
                messageNode.set("message", messageBodyNode);

                return messageNode;
            }
        }).collect(Collectors.toList());

        return result;
    }
}
