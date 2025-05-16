package com.example.pagamento_backend.business.service;

import com.example.pagamento_backend.infrastructure.dtos.AuthorizationResponceDto;
import com.example.pagamento_backend.infrastructure.exceptions.AuthorizationException;
import com.example.pagamento_backend.infrastructure.entities.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    @Value("${service.authorization.url}")
    private String urlAuthorize;

    public void authorize(Transaction transaction) {

        RestClient restClient = RestClient.builder().baseUrl(urlAuthorize).build();

        AuthorizationResponceDto body = restClient
                .get()
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new AuthorizationException("Ocorreu um erro no preocesso de autorização");
                })
                .toEntity(AuthorizationResponceDto.class).getBody();

        if(!body.getData().isAuthorization()) {
            throw new AuthorizationException("Operação não foi autorizada");
        }
    }

}
