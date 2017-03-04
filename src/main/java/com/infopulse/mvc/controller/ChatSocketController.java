package com.infopulse.mvc.controller;

import com.google.gson.JsonArray;
import com.infopulse.mvc.domain.Message;
import com.infopulse.mvc.domain.User;
import com.infopulse.mvc.dto.UserDTO;
import com.infopulse.mvc.service.ChatService;
import com.infopulse.mvc.service.RedisService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vedmant on 2/25/17.
 */
public class ChatSocketController extends TextWebSocketHandler {

    private static Map<String, WebSocketSession> clientsOnline = new HashMap<>();

    @Autowired
    private RedisService redisService;

    @Autowired
    private ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("handleTextMessage");
        Gson gson = new Gson();
        Type jsontType = new TypeToken<HashMap<String, String>>() {}.getType();
        Map<String, String> entry = gson.fromJson(message.getPayload(), jsontType);

        String key = entry.keySet().iterator().next();
        String value = entry.values().iterator().next();

        System.out.println("Key: " + key);
        System.out.println("Value: " + value);

        if (key == null) {
            session.sendMessage(new TextMessage("Bad"));
            return;
        }

        if (key.equals("sessionId")) {
            UserDTO user = chatService.authUser(value);
            authUser(session, user);
            return;
        }

        System.out.println("Authorize");
        if (! isAuthorized(session)) {
            session.sendMessage(new TextMessage("Bad"));
            return;
        }

        switch (key) {
            case "list":
                JsonObject list = new JsonObject();
                list.add("list", gson.toJsonTree(clientsOnline.keySet()));
                session.sendMessage(new TextMessage(list.toString()));
                break;
            case "broadcast":
                sendMessage(session, false, value, null);
                break;
            default:
                sendMessage(session, true, value, key);
                break;
        }
    }

    private boolean isAuthorized(WebSocketSession session) {
        return clientsOnline.values().contains(session);
    }

    private void sendMessage(WebSocketSession session, boolean isPrivate, String message, String receiver) throws IOException {
        String sender = clientsOnline.entrySet().stream()
                .filter(item -> item.getValue() == session)
                .findFirst()
                .orElse(null)
                .getKey();

        System.out.println("Send message");
        System.out.println("Sender: " + sender);
        System.out.println("Receiver: " + receiver);

        if (isPrivate) {
            if (session != null) {
                JsonObject privateMessage = new JsonObject();
                privateMessage.addProperty("sender", sender);
                privateMessage.addProperty("receiver", receiver);
                privateMessage.addProperty("message", message);
                clientsOnline.get(receiver).sendMessage(new TextMessage(privateMessage.toString()));
                session.sendMessage(new TextMessage(privateMessage.toString()));
            } else
                chatService.saveMessage(message, sender, receiver);
        } else {
            JsonObject broadcastMessage = new JsonObject();
            broadcastMessage.addProperty("sender", sender);
            broadcastMessage.addProperty("message", message);

//            Jedis jedis = redisService.getJedis();

//            jedis.lpush("broadcast", broadcastMessage.getAsString());
            for (WebSocketSession socketSession : clientsOnline.values()) {
                System.out.println("Broadcast message");
                System.out.println(socketSession.toString());
                System.out.println(broadcastMessage.toString());
                socketSession.sendMessage(new TextMessage(broadcastMessage.toString()));
            }
        }
    }

    private void authUser(WebSocketSession session, UserDTO user) throws IOException {
        Gson gson = new Gson();
        if (user != null) {
            session.sendMessage(new TextMessage("{'auth':'yes'}"));
            clientsOnline.put(user.getLogin(), session);
            List<Message> messages = chatService.getAllMessagesByUserLogin(user.getLogin());

            messages.forEach(userMessage -> {
                try {
                    session.sendMessage(new TextMessage(gson.toJson(userMessage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

//            List<Message> broadcastMessages = redisService.getBroadcastMessages();
//            broadcastMessages.forEach(broadcastMessage -> {
//                try {
//                    session.sendMessage(new TextMessage(gson.toJson(broadcastMessage, Message.class)));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });

        } else {
            session.sendMessage(new TextMessage("{'auth':'no'}"));
        }
    }
}