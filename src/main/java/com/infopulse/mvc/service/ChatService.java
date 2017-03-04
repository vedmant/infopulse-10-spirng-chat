package com.infopulse.mvc.service;

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
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    public UserDTO authUser(String sessionId) {
        System.out.println("Chat service");
        HttpSession httpSession = HttpSessionListenerImpl.getSessionById(sessionId);
        System.out.println("Session: " + httpSession.toString());
        return (UserDTO) httpSession.getAttribute("user");
    }

    public List<Message> getAllMessagesByUserLogin(String login) {
        return messageRepository.getAllMessagesByReceiverLogin(login);
    }

    public void saveMessage(String message, String sender, String receiver) {
        Message newMessage = new Message();
        User senderUser = userRepository.findUserByLogin(sender);
        User receiverUser = userRepository.findUserByLogin(receiver);
        newMessage.setBody(message);
        newMessage.setReceiver(receiverUser);
        newMessage.setSender(senderUser);

        messageRepository.save(newMessage);
    }
}