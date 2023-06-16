package com.prestamossb.prestamossbapi.domain.client;

import com.prestamossb.prestamossbapi.domain.loan.Loan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false ,name = "last_name")
    private String lastName;

    @Column(nullable = false)
    private String phone;

    @Column
    private String address;

    @Column(nullable = false)
    private String email;

    @Column(name = "deleted",columnDefinition = "boolean default false")
    private boolean deleted;

    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "client")
    private List<Loan> loans;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return deleted == client.deleted && Objects.equals(id, client.id) && Objects.equals(name, client.name) && Objects.equals(lastName, client.lastName) && Objects.equals(phone, client.phone) && Objects.equals(address, client.address) && Objects.equals(email, client.email) && Objects.equals(createAt, client.createAt) && Objects.equals(updateAt, client.updateAt) && Objects.equals(loans, client.loans);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, phone, address, email, deleted, createAt, updateAt, loans);
    }
}
