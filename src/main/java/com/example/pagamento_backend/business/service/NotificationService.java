package com.example.pagamento_backend.business.service;

import com.example.pagamento_backend.infrastructure.entities.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationProducer notificationProducer;

    public void notify(Transaction transaction) {
        notificationProducer.sendNotification(transaction);
    }

}
