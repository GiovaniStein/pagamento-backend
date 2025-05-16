package com.example.pagamento_backend.infrastructure.exceptions;

public class NotificationException extends RuntimeException {
  public NotificationException(String message) {
    super(message);
  }
}
