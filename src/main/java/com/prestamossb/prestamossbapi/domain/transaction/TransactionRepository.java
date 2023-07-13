package com.prestamossb.prestamossbapi.domain.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction, UUID> {
    Optional<List<Transaction>> findAllByLoanIdAndDeletedFalse(UUID loanId);
    Optional<List<Transaction>> findAllByUserIdAndDeletedFalse(UUID userId);
}
