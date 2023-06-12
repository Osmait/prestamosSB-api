package com.prestamossb.prestamossbapi.domain.loan;

import com.prestamossb.prestamossbapi.domain.client.Client;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double amount;

    @Column(name = "payment_date",nullable = false)
    private LocalDateTime PaymentDate;

    @Column(nullable = false)
    private Double interest;

    @Column(name ="amount_of_payments",nullable = false)
    private Integer amountOfPayments;

    @Column(name = "is_paid",columnDefinition = "boolean default false")
    private boolean isPaid;

    @Column(nullable = false)
    private Frequency frequency;

    @Column(name = "deleted",columnDefinition = "boolean default false")
    private  boolean deleted;

    @Column(name = "create_at" )
    @CreationTimestamp
    private LocalDateTime CreateAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime UpdateAt;

    @ManyToOne
    @JoinColumn(name = "client_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Client client;
}
