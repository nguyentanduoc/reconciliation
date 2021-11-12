package com.ntd.bank.transactionservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "transactions")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "content")
    private String content;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "type")
    private String type;
}
