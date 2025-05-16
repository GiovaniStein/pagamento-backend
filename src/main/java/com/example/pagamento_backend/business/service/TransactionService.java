package com.example.pagamento_backend.business.service;

import com.example.pagamento_backend.business.mapper.TransactionMapper;
import com.example.pagamento_backend.infrastructure.dtos.CreateTransactionDto;
import com.example.pagamento_backend.infrastructure.dtos.TransactionResponceDto;
import com.example.pagamento_backend.infrastructure.exceptions.TransactionValidationException;
import com.example.pagamento_backend.infrastructure.entities.Transaction;
import com.example.pagamento_backend.infrastructure.repositories.TransactionRepository;
import com.example.pagamento_backend.infrastructure.entities.Wallet;
import com.example.pagamento_backend.infrastructure.repositories.WalletRepository;
import com.example.pagamento_backend.infrastructure.enums.WalletType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;
    private final TransactionMapper transactionMapper;

    @Transactional
    public TransactionResponceDto create(CreateTransactionDto transaction) {

        Wallet payee = walletRepository.findById(transaction.getPayee())
                .orElseThrow(() -> new TransactionValidationException("Carteira recebimento não encontrada"));

        Wallet payer = walletRepository.findById(transaction.getPayer())
                .orElseThrow(() -> new TransactionValidationException("Carteira pagadora não encontrado"));

        validade(payee, payer);

        Transaction newTransaction = new Transaction();
        newTransaction.setPayee(payee);
        newTransaction.setPayer(payer);
        newTransaction.setValor(transaction.getValor());

        Transaction saveTransaction = transactionRepository.save(newTransaction);

        payer.debit(transaction.getValor());
        walletRepository.save(payer);
        payee.credit(transaction.getValor());
        walletRepository.save(payee);

        authorizationService.authorize(saveTransaction);

        notificationService.notify(newTransaction);

        return transactionMapper.toTransactionResponceDto(saveTransaction);
    }

    private void validade(Wallet payee, Wallet payer) {
        if (payee.getId().equals(payer.getId())) {
            throw new TransactionValidationException("Carteiras de pagamento e recebimento não podem ser iguais");
        }

        if (payer.getType() == WalletType.LOJISTA) {
            throw new TransactionValidationException("Carteiras pagadora não pode ser do tipo Lojista");
        }
    }

    public List<TransactionResponceDto> listAll() {
        List<Transaction> all = transactionRepository.findAll();
        return all.stream().map(transactionMapper::toTransactionResponceDto).toList();
    }




}
