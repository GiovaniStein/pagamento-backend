package com.example.pagamento_backend.business.service;

import com.example.pagamento_backend.infrastructure.dtos.AuthorizationResponceDto;
import com.example.pagamento_backend.infrastructure.exceptions.AuthorizationException;
import com.example.pagamento_backend.infrastructure.entities.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    @Value("${service.authorization.url}")
    private String urlAuthorize;
    private final RestClient restClient;

    public void authorize(Transaction transaction) {

        ResponseEntity<AuthorizationResponceDto> request = restClient
                .method(HttpMethod.GET)
                .uri(urlAuthorize)
                .retrieve()
                .toEntity(AuthorizationResponceDto.class);

        if (!request.getStatusCode().is2xxSuccessful()) {
            throw new AuthorizationException("Ocorreu um erro no preocesso de autorização");
        }

        if (request.getStatusCode().is2xxSuccessful() && !request.getBody().getData().isAuthorization()) {
            throw new AuthorizationException("Operação não foi autorizada");
        }

    }

}
