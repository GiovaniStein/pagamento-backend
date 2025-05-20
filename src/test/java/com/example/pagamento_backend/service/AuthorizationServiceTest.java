package com.example.pagamento_backend.service;

import com.example.pagamento_backend.business.service.AuthorizationService;
import com.example.pagamento_backend.infrastructure.dtos.AuthorizationDto;
import com.example.pagamento_backend.infrastructure.dtos.AuthorizationResponceDto;
import com.example.pagamento_backend.infrastructure.entities.Transaction;
import com.example.pagamento_backend.infrastructure.exceptions.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTest {

    @Mock
    private RestClient.RequestBodyUriSpec restClientBody;
    @Mock
    private RestClient restClient;
    @InjectMocks
    private AuthorizationService authorizationService;
    private AuthorizationResponceDto authorizationResponceDto;
    private String urlAuthorize;

    @BeforeEach
    void before() {
        urlAuthorize = "http://localhost:8081";
        ReflectionTestUtils.setField(authorizationService, "urlAuthorize", urlAuthorize);
        authorizationResponceDto = new AuthorizationResponceDto("Ok", new AuthorizationDto(true));
    }

    @Test
    void authorizeSucesso() {

        RestClient.RequestBodyUriSpec requestBodyUriSpec = mock(RestClient.RequestBodyUriSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        when(restClient.method(HttpMethod.GET)).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(urlAuthorize)).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toEntity(AuthorizationResponceDto.class)).thenReturn(ResponseEntity.ok(authorizationResponceDto));

        assertDoesNotThrow(() -> authorizationService.authorize(new Transaction()));
    }

    @Test
    void authorizeErroRequisicao() {

        RestClient.RequestBodyUriSpec requestBodyUriSpec = mock(RestClient.RequestBodyUriSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        when(restClient.method(HttpMethod.GET)).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(urlAuthorize)).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toEntity(AuthorizationResponceDto.class)).thenReturn(ResponseEntity.badRequest().build());

        assertThrows(AuthorizationException.class, () ->
                authorizationService.authorize(new Transaction()));
    }

    @Test
    void authorizeAutenticacaoNegada() {

        RestClient.RequestBodyUriSpec requestBodyUriSpec = mock(RestClient.RequestBodyUriSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);
        when(restClient.method(HttpMethod.GET)).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(urlAuthorize)).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);

        authorizationResponceDto.setData(new AuthorizationDto(false));
        when(responseSpec.toEntity(AuthorizationResponceDto.class)).thenReturn(ResponseEntity.ok(authorizationResponceDto));

        assertThrows(AuthorizationException.class, () ->
                authorizationService.authorize(new Transaction()));
    }


}
