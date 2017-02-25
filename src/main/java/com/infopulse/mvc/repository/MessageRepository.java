package com.infopulse.mvc.repository;

import com.infopulse.mvc.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vedmant on 2/25/17.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select m from com.infopulse.mvc.domain.Message m join m.receiver r where r.login = :login")
    List<Message> getAllMessagesByRecieverLogin(@Param("login") String login);

    @Modifying
    @Query("delete from com.infopulse.mvc.domain.Message m where m.receiver.login = :login")
    void deleteAllMessagesByRecieverLogin(@Param("login") String login);
}
