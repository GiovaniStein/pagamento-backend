package com.example.pagamento_backend.infrastructure.entities;

import com.example.pagamento_backend.infrastructure.utils.Formater;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet payer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet payee;
    private BigDecimal valor;
    @CreatedDate
    private LocalDateTime createdAt;

    public String madeMassage() {
        return String.format("Transação ID=(%s) recebida com valor de R$ %s", this.id, Formater.formaterCurrency(this.valor));
    }
}
