package com.hugosrc.banking.account;

import com.hugosrc.banking.person.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts", uniqueConstraints = {
        @UniqueConstraint(name = "unique_account_number_digit", columnNames = {"number", "digit"})
})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "digit", nullable = false)
    private int digit;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AccountType type;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    public Account(int number, int digit, AccountType type, BigDecimal balance, Person person) {
        this.number = number;
        this.digit = digit;
        this.type = type;
        this.balance = balance;
        this.person = person;
    }
}
