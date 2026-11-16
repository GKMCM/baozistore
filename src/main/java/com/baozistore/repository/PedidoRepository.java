package com.baozistore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baozistore.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // consultas derivadas do nome do metodo (Spring Data JPA)
    boolean existsByClienteId(Long clienteId);

    boolean existsByProdutoId(Long produtoId);
}
