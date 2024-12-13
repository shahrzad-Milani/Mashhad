package website.mashhad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import website.mashhad.model.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByMessageIdOrderByRepliedAtDesc(Long messageId);
}
