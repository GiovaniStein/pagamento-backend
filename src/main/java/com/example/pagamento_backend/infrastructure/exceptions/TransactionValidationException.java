package com.example.pagamento_backend.infrastructure.exceptions;

public class TransactionValidationException extends RuntimeException {
    public TransactionValidationException(String message) {
        super(message);
    }
}
