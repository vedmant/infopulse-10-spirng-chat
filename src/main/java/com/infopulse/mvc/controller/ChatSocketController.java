package com.infopulse.mvc.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.infopulse.mvc.domain.Message;
import com.infopulse.mvc.dto.UserDTO;
import com.infopulse.mvc.service.ChatService;
import com.infopulse.mvc.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import redis.clients.jedis.Jedis;
import java.io.IOException;
import java.util.*;

/**
 * Created by vedmant on 2/25/17.
 */
public class ChatSocketController extends TextWebSocketHandler {

    static Map<String,WebSocketSession> clientsOnline = new HashMap<>();

    @Autowired
    private RedisService redisService;

    @Autowired
    private ChatService chatService;

    @Override
    @Transactional
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String recievedMessage = message.getPayload();

        ObjectMapper mapper = new ObjectMapper();
        JsonParser jp = mapper.getFactory().createParser(recievedMessage);
        JsonNode json = mapper.readTree(jp);

        Iterator<Map.Entry<String,JsonNode>> rootFields = json.fields();

        Map.Entry<String,JsonNode> firstField = rootFields.next();
        if (firstField.getKey() == null) {
            session.sendMessage(new TextMessage("bad_request"));
            return;
        }

        if (firstField.getKey().equals("sessionId")) {
            String sessionId = firstField.getValue().asText();
            UserDTO user = chatService.authorizeUser(sessionId);

            if (user != null) {
                clientsOnline.put(user.getLogin(), session);

                List<Message> messages = chatService.getAllMessagesByUser(user);

                messages.forEach(message1 -> {
                    ObjectNode privateMessage = mapper.createObjectNode();
                    JsonNode senderNode = mapper.createObjectNode().path(message1.getSender().getLogin());
                    JsonNode messageNode = mapper.createObjectNode().path(message1.getBody());
                    privateMessage.set("name", senderNode);
                    privateMessage.set("message", messageNode);
                    try {
                        session.sendMessage(new TextMessage(privateMessage.asText()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                List<ObjectNode> redisMessages = redisService.getAllMessages();
                redisMessages.forEach(redisMessage -> {
                    try {
                        session.sendMessage(new TextMessage(redisMessage.asText()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            } else {
                session.sendMessage(new TextMessage("{\"auth\":\"error\"}"));
            }

            return;
        }

        if (firstField.getKey().equals("list")) {
            List<String> userList = new ArrayList<>(clientsOnline.keySet());
            ObjectNode listNode = mapper.createObjectNode();
            ArrayNode usersNode = mapper.createArrayNode();
            userList.forEach(usersNode::add);
            listNode.set("list", usersNode);

            session.sendMessage(new TextMessage(listNode.asText()));

            return;
        }

        if (firstField.getKey().equals("broadcast")) {
            String sender = clientsOnline.entrySet().stream()
                    .filter(item -> item.getValue() == session)
                    .findFirst()
                    .get()
                    .getKey();

            ObjectNode broadcastMessage = mapper.createObjectNode();
            JsonNode senderNode = mapper.createObjectNode().path(sender);
            broadcastMessage.set("name", senderNode);
            broadcastMessage.set("message", firstField.getValue());

            Jedis jedis = redisService.getJedis();
            jedis.lpush("broadcast", sender + ":" + firstField.getValue().asText());

            clientsOnline.entrySet().stream()
                    .forEach(clientSession -> {
                        try {
                            clientSession.getValue().sendMessage(new TextMessage(broadcastMessage.asText()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            return;
        }

        String userName = firstField.getKey();

        String sender = clientsOnline.entrySet().stream()
                .filter(item -> item.getValue() == session)
                .findFirst()
                .get()
                .getKey();

        WebSocketSession recieverSession = clientsOnline.get(userName);

        if (recieverSession != null) {
            ObjectNode privateMessage = mapper.createObjectNode();
            JsonNode senderNode = mapper.createObjectNode().path(sender);
            privateMessage.set("name", senderNode);
            privateMessage.set("message", firstField.getValue());

            recieverSession.sendMessage(new TextMessage(privateMessage.asText()));
        } else {
            chatService.saveMessage(firstField.getValue().asText(), sender, firstField.getKey());
        }

    }
}
