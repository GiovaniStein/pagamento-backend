package com.example.pagamento_backend.infrastructure.repositories;

import com.example.pagamento_backend.infrastructure.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
