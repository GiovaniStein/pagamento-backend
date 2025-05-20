package com.example.pagamento_backend.service;

import com.example.pagamento_backend.business.mapper.TransactionMapper;
import com.example.pagamento_backend.business.service.AuthorizationService;
import com.example.pagamento_backend.business.service.NotificationService;
import com.example.pagamento_backend.business.service.TransactionService;
import com.example.pagamento_backend.infrastructure.dtos.CreateTransactionDto;
import com.example.pagamento_backend.infrastructure.dtos.TransactionResponceDto;
import com.example.pagamento_backend.infrastructure.entities.Transaction;
import com.example.pagamento_backend.infrastructure.entities.Wallet;
import com.example.pagamento_backend.infrastructure.enums.WalletType;
import com.example.pagamento_backend.infrastructure.exceptions.AuthorizationException;
import com.example.pagamento_backend.infrastructure.exceptions.TransactionValidationException;
import com.example.pagamento_backend.infrastructure.repositories.TransactionRepository;
import com.example.pagamento_backend.infrastructure.repositories.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private AuthorizationService authorizationService;
    @Mock
    private NotificationService notificationService;
    @Mock
    private TransactionMapper transactionMapper;
    @InjectMocks
    private TransactionService transactionService;

    private Wallet payer;
    private Wallet payee;
    private CreateTransactionDto createTransactionDto;
    private Transaction transaction;
    private TransactionResponceDto transactionResponceDto;

    @BeforeEach
    void before() {
        payer = new Wallet(1L,
                "João Teste",
                "11111111111",
                "joao@teste.com",
                "1234",
                WalletType.COMUM,
                BigDecimal.valueOf(1500));

        payee = new Wallet(2L,
                "João Lojista",
                "22222222222",
                "joao@teste2.com",
                "1234",
                WalletType.LOJISTA,
                BigDecimal.valueOf(1500));

        final LocalDateTime createdAt = LocalDateTime.of(2025, 5, 19, 1, 1, 1);

        transaction = new Transaction(1L, payer, payee, BigDecimal.TEN, createdAt);

        createTransactionDto = new CreateTransactionDto(1L, 2L, BigDecimal.TEN);

        transactionResponceDto = new TransactionResponceDto(1L,
                "11111111111",
                "Nome Teste",
                "22222222222",
                "Nome Teste2",
                BigDecimal.TEN,
                createdAt);
    }

    @Test
    void createSucesso() {
        when(walletRepository.findById(1L)).thenReturn(Optional.of(payer));
        when(walletRepository.findById(2L)).thenReturn(Optional.of(payee));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.toTransactionResponceDto(any(Transaction.class))).thenReturn(transactionResponceDto);

        TransactionResponceDto sut = transactionService.create(createTransactionDto);
        assertThat(sut).isEqualTo(transactionResponceDto);
    }

    @Test
    void createErroCarteirasIguais() {
        when(walletRepository.findById(1L)).thenReturn(Optional.of(payer));

        createTransactionDto.setPayee(1L);

        assertThrows(TransactionValidationException.class, () ->
                transactionService.create(createTransactionDto));
    }

    @Test
    void createErroPagadorTipoLojista() {
        when(walletRepository.findById(1L)).thenReturn(Optional.of(payee));
        when(walletRepository.findById(2L)).thenReturn(Optional.of(payer));

        assertThrows(TransactionValidationException.class, () ->
                transactionService.create(createTransactionDto));
    }

    @Test
    void createErroSaldoInsuficiente() {
        when(walletRepository.findById(1L)).thenReturn(Optional.of(payer));
        when(walletRepository.findById(2L)).thenReturn(Optional.of(payee));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        createTransactionDto.setValor(BigDecimal.valueOf(10000));

        assertThrows(TransactionValidationException.class, () ->
                transactionService.create(createTransactionDto));
    }


}
