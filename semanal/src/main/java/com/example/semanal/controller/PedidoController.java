package com.example.semanal.controller;

import com.example.semanal.entity.Pedido;
import com.example.semanal.repository.PedidoRepository;
import com.example.semanal.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/pedido")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @Autowired
    PedidoRepository pedidoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findByIDPath(@PathVariable("id") final String id) {
        final Pedido pedido = this.pedidoRepository.findById(id).orElse(null);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listAll() {
        return ResponseEntity.ok(this.pedidoRepository.findAll());
    }

    @PostMapping
    public ResponseEntity <HttpStatus> createPedido(@RequestBody final Pedido pedido) {
        try {
            this.pedidoService.save(pedido);
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestBody Pedido pedidoAtualizado) {
        return pedidoService.updateStatus(id, pedidoAtualizado.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletarPedido(@PathVariable("id") final String id) {
        try {
            this.pedidoService.deletarPedido(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
