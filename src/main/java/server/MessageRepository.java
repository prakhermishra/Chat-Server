package server;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

    public Message findByMessageId(Long id);

    public void deleteAllByUserName(String userName);

    public List<Message> findAllByUserName(String userName);

    public List<Message> findAllByUserNameAndMessageIdGreaterThanEqual(String userName, Long messageId);

    //public List<Message> findAllByUserNameOrderByMessage(String userName, Long messageId, Long limit);

    @Query(nativeQuery = true, value = "SELECT * FROM message WHERE user_name = ?1 AND message_id >= ?2 ORDER  BY message_id DESC LIMIT ?3")
    List<Message> findTopNUserNameOrderByMessageIdDescTillLimit(String userName, Long messageId, int limit);


}