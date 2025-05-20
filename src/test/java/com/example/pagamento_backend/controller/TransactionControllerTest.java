package com.example.pagamento_backend.controller;

import com.example.pagamento_backend.business.service.TransactionService;
import com.example.pagamento_backend.infrastructure.dtos.CreateTransactionDto;
import com.example.pagamento_backend.infrastructure.dtos.TransactionResponceDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;
    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;
    private CreateTransactionDto createTransactionDto;
    private TransactionResponceDto transactionResponceDto;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();

        objectMapper.registerModule(new JavaTimeModule());

        final LocalDateTime createdAt = LocalDateTime.of(2025, 5, 19, 1, 1, 1);

        createTransactionDto = new CreateTransactionDto(1L, 1L, BigDecimal.TEN);

        transactionResponceDto = new TransactionResponceDto(1L,
                "11111111111",
                "Nome Teste",
                "22222222222",
                "Nome Teste2",
                BigDecimal.TEN,
                createdAt);
    }

    @Test
    void createTransactionSucesso() throws Exception {
        when(transactionService.create(createTransactionDto)).thenReturn(transactionResponceDto);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createTransactionDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.payerCpf").value(transactionResponceDto.getPayerCpf()))
                .andExpect(jsonPath("$.payerName").value(transactionResponceDto.getPayerName()))
                .andExpect(jsonPath("$.payeeCpf").value(transactionResponceDto.getPayeeCpf()))
                .andExpect(jsonPath("$.payeeName").value(transactionResponceDto.getPayeeName()))
                .andExpect(jsonPath("$.valor").value(transactionResponceDto.getValor().toString()))
                .andExpect(jsonPath("$.createdAt").value(transactionResponceDto.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))));

        verify(transactionService, times(1)).create(createTransactionDto);
    }

    @Test
    void findAllTransactionSucesso() throws Exception {
        when(transactionService.listAll()).thenReturn(List.of(transactionResponceDto));

        mockMvc.perform(get("/transaction")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].payerCpf").value(transactionResponceDto.getPayerCpf()))
                .andExpect(jsonPath("$[0].payerName").value(transactionResponceDto.getPayerName()))
                .andExpect(jsonPath("$[0].payeeCpf").value(transactionResponceDto.getPayeeCpf()))
                .andExpect(jsonPath("$[0].payeeName").value(transactionResponceDto.getPayeeName()))
                .andExpect(jsonPath("$[0].valor").value(transactionResponceDto.getValor().toString()))
                .andExpect(jsonPath("$[0].createdAt").value(transactionResponceDto.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))));

        verify(transactionService, times(1)).listAll();
    }

}
