package com.example.pagamento_backend.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorObjectDto {
    private String message;
    private String field;
    private Object parameter;
}
