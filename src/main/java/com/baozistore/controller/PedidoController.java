package com.baozistore.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.baozistore.model.Cliente;
import com.baozistore.model.Pedido;
import com.baozistore.model.Produto;
import com.baozistore.repository.ClienteRepository;
import com.baozistore.repository.PedidoRepository;
import com.baozistore.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    // POST /pedidos - registra um pedido (201 Created)
    // corpo: { "cliente": { "id": 1 }, "produto": { "id": 1 }, "quantidade": 12 }
    @PostMapping
    public ResponseEntity<Pedido> criar(@Valid @RequestBody Pedido pedido) {
        pedido.setId(null); // o id e gerado pelo banco
        pedido.setCliente(buscarCliente(pedido.getCliente()));
        pedido.setProduto(buscarProduto(pedido.getProduto()));
        Pedido salvo = pedidoRepository.save(pedido);
        return ResponseEntity.created(URI.create("/pedidos/" + salvo.getId())).body(salvo);
    }

    // GET /pedidos - lista todos os pedidos
    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    // GET /pedidos/{id} - consulta um pedido pelo id (404 se nao existir)
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /pedidos/{id} - atualiza um pedido existente (opcional no trabalho)
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @Valid @RequestBody Pedido dados) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setCliente(buscarCliente(dados.getCliente()));
                    pedido.setProduto(buscarProduto(dados.getProduto()));
                    pedido.setQuantidade(dados.getQuantidade());
                    return ResponseEntity.ok(pedidoRepository.save(pedido));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /pedidos/{id} - apaga um pedido (204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // O JSON traz apenas o id; aqui o cliente completo e carregado do banco.
    private Cliente buscarCliente(Cliente cliente) {
        if (cliente.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o id do cliente.");
        }
        return clienteRepository.findById(cliente.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Cliente " + cliente.getId() + " nao encontrado."));
    }

    // O JSON traz apenas o id; aqui o produto completo e carregado do banco.
    private Produto buscarProduto(Produto produto) {
        if (produto.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o id do produto.");
        }
        return produtoRepository.findById(produto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Produto " + produto.getId() + " nao encontrado."));
    }
}
