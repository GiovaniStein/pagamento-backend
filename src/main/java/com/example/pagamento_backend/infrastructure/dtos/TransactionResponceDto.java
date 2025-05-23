package com.example.pagamento_backend.infrastructure.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponceDto {
    private Long id;
    private String payerCpf;
    private String payerName;
    private String payeeCpf;
    private String payeeName;
    private BigDecimal valor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")


    private LocalDateTime createdAt;
}
