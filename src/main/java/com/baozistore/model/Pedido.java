package com.baozistore.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Pedido simples: um cliente compra um unico produto em determinada quantidade.
 *
 * Os relacionamentos @ManyToOne geram, na tabela "pedido", as chaves
 * estrangeiras cliente_id e produto_id (os campos clienteId e produtoId do DER).
 * No JSON, o pedido e enviado assim:
 * { "cliente": { "id": 1 }, "produto": { "id": 1 }, "quantidade": 12 }
 */
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // muitos pedidos podem ser do mesmo cliente
    @NotNull(message = "o cliente do pedido e obrigatorio")
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // muitos pedidos podem ser do mesmo produto
    @NotNull(message = "o produto do pedido e obrigatorio")
    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @NotNull(message = "a quantidade e obrigatoria")
    @Positive(message = "a quantidade deve ser maior que zero")
    @Column(nullable = false)
    private Integer quantidade;

    public Pedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
