package com.example.pagamento_backend.infrastructure.repositories;

import com.example.pagamento_backend.infrastructure.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
