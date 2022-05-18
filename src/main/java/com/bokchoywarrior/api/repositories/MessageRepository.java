package com.bokchoywarrior.api.repositories;

import com.bokchoywarrior.api.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
