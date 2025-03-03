package com.example.semanal.entity;

import com.example.semanal.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@Table
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    public String id;

    @Column(name = "cliente", nullable = false)
    public String cliente;

    @Column(name = "email", nullable = false)
    public String email;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    public List<Item> itens;

    @Column(name = "total", nullable = false)
    public double total;

    @Column(name = "status")
    @Enumerated(EnumType.STRING) // Adicionando para garantir que o valor será armazenado como STRING
    private Status status;

    @Column(name = "data_criacao")
    public LocalDateTime data_criacao;

    @Column(name = "data_atualizacao")
    public LocalDateTime data_atualizacao;

    @PrePersist
    public void prePersist() {
        this.data_criacao = LocalDateTime.now();
        this.status = Status.PENDENTE; // Ajuste para garantir que o status seja PENDENTE
    }

    @PreUpdate
    public void preUpdate() {
        this.data_atualizacao = LocalDateTime.now();
    }

    public void calcularTotal() {
        this.total = itens.stream()
                .mapToDouble(item -> item.getPreco() * item.getQuantidade())
                .sum();
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
