package com.example.pagamento_backend.business.mapper;

import com.example.pagamento_backend.infrastructure.entities.Transaction;
import com.example.pagamento_backend.infrastructure.dtos.TransactionResponceDto;
import com.example.pagamento_backend.infrastructure.entities.Wallet;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponceDto toTransactionResponceDto(Transaction transaction) {
        TransactionResponceDto transactionResponce = new TransactionResponceDto();
        transactionResponce.setId(transaction.getId());
        transactionResponce.setValor(transaction.getValor());
        transactionResponce.setCreatedAt(transaction.getCreatedAt());

        Wallet payer = transaction.getPayer();
        Wallet payee = transaction.getPayee();

        transactionResponce.setPayerCpf(payer.getCpf());
        transactionResponce.setPayerName(payer.getFullName());
        transactionResponce.setPayeeCpf(payee.getCpf());
        transactionResponce.setPayeeName(payee.getFullName());

        return transactionResponce;
    }

}
