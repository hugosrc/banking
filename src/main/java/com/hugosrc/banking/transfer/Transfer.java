package com.hugosrc.banking.transfer;

import com.hugosrc.banking.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account from;

    @ManyToOne
    @JoinColumn(name = "to_account_id", nullable = false)
    private Account to;

    public Transfer(BigDecimal amount, Account from, Account to) {
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.date = new Date();
    }
}
