package com.pyro.repositories;

import com.pyro.entities.Message;
import com.pyro.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message>  findByUser(User user);
    List<Message>  findByUserOrderByDateDesc(User user);

    @Query(nativeQuery = true, value =
           "select header from message where message.customer = ?1 group by header ;")
    List<String> findHeadersByUserIdGroupByHeader(Long userId);
}
