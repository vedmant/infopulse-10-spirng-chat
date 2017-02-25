package com.infopulse.mvc.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.infopulse.mvc.domain.Message;
import com.infopulse.mvc.domain.User;
import com.infopulse.mvc.dto.UserDTO;
import com.infopulse.mvc.listener.HttpSessionListenerImpl;
import com.infopulse.mvc.repository.MessageRepository;
import com.infopulse.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by vedmant on 2/25/17.
 */
@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public UserDTO authorizeUser(String sessionId) {
        HttpSession httpSession = HttpSessionListenerImpl.getSessionById(sessionId);
        return (UserDTO) httpSession.getAttribute("user");
    }

    public List<Message> getAllMessagesByUser(UserDTO user) {
        List<Message> messages = messageRepository.getAllMessagesByRecieverLogin(user.getLogin());

//        messageRepository.deleteAllMessagesByRecieverLogin(user.getLogin());

        return messages;
    }

    public void saveMessage(String messageBody, String senderLogin, String receiverLogin) {
        User sender = userRepository.findByLogin(senderLogin);
        User receiver = userRepository.findByLogin(receiverLogin);

        Message message = new Message();
        message.setBody(messageBody);
        message.setSender(sender);
        message.setReceiver(receiver);

        messageRepository.save(message);
    }
}
