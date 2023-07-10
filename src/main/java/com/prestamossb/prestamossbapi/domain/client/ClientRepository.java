package com.prestamossb.prestamossbapi.domain.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<List<Client>> findAllByUserIdAndDeletedFalse(UUID userId);
}
