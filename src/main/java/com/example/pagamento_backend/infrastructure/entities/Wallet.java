package com.example.pagamento_backend.infrastructure.entities;

import com.example.pagamento_backend.infrastructure.exceptions.TransactionValidationException;
import com.example.pagamento_backend.infrastructure.enums.WalletType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_wallet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Column(name = "cpf", unique = true)
    private String cpf;
    @Column(name = "email", unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private WalletType type;
    private BigDecimal balance;

    public void debit(BigDecimal value) {

        if (this.balance.compareTo(value) < 0) {
            throw new TransactionValidationException("Valor a debitar maior que o saldo em carteira");
        }

        this.balance = this.balance.subtract(value);
    }

    public void credit(BigDecimal value) {
        this.balance = this.balance.add(value);
    }
}
