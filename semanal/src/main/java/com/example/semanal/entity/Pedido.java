package com.example.semanal.entity;

import com.example.semanal.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
@Table
public class Pedido {
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(LocalDateTime data_criacao) {
        this.data_criacao = data_criacao;
    }

    public LocalDateTime getData_atualizacao() {
        return data_atualizacao;
    }

    public void setData_atualizacao(LocalDateTime data_atualizacao) {
        this.data_atualizacao = data_atualizacao;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "cliente", nullable = false)
    private String cliente;

    @Column(name = "email", nullable = false)
    private String email;

    public double getTotal() {
        return total;
    }

    @Column(name = "itens", nullable = false)
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List <Item> itens;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "status")
    private Status status;

    @Column(name = "data_criacao")
    private LocalDateTime data_criacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime data_atualizacao;

    @PrePersist
    private void prePersist ()
    {
        this.data_criacao = LocalDateTime.now();
        this.status = status.PENDENTE;
    }

    @PreUpdate
    private void preUpdate ()
    {
        this.data_atualizacao = LocalDateTime.now();
    }

    public void calcularTotal() {
        this.total = itens.stream()
                .mapToDouble(item -> item.getPreco() * item.getQuantidade())
                .sum();
    }

}
