package com.example.pagamento_backend.infrastructure.repositories;

import com.example.pagamento_backend.infrastructure.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
