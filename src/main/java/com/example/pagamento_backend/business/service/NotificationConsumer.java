package com.example.pagamento_backend.business.service;

import com.example.pagamento_backend.infrastructure.entities.Notification;
import com.example.pagamento_backend.infrastructure.repositories.NotificationRepository;
import com.example.pagamento_backend.infrastructure.entities.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    @Value("${service.notification.url}")
    private String urlNotification;
    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "transaction-notification", groupId = "pagamento-backend")
    public void receiveNotification(Transaction transaction) {
        RestClient restClient = RestClient.builder().baseUrl(urlNotification).build();

        ResponseEntity<String> response = restClient.post()
                .retrieve().toEntity(String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Notification notification = new Notification();
            notification.setMessage(transaction.madeMassage());
            notificationRepository.save(notification);
        }

    }

}
