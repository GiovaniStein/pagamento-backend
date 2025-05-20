package com.example.pagamento_backend.controller;

import com.example.pagamento_backend.business.service.TransactionService;
import com.example.pagamento_backend.infrastructure.dtos.CreateTransactionDto;
import com.example.pagamento_backend.infrastructure.dtos.TransactionResponceDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponceDto> createTransaction(@Valid @RequestBody CreateTransactionDto transaction) {
        TransactionResponceDto save = transactionService.create(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponceDto>> findAllTransaction() {
        List<TransactionResponceDto> transactionsResponce = transactionService.listAll();
        return ResponseEntity.ok(transactionsResponce);
    }

}
