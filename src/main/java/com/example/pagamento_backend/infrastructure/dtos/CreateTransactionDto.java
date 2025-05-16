package com.example.pagamento_backend.infrastructure.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionDto {
    @NotNull
    private Long payer;
    @NotNull
    private Long payee;
    @NotNull
    @DecimalMin(value = "1")
    private BigDecimal valor;
}
