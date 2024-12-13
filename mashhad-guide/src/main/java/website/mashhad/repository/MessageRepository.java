package website.mashhad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import website.mashhad.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
