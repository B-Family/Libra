package org.libra.repositories;

import org.libra.entities.implementation.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer>
{
    Optional<MessageEntity> findMessageEntityById(Integer id);

    @Modifying
    @Transactional
    void deleteMessageEntityById(Integer id);
}