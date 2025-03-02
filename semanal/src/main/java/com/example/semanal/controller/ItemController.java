package com.example.semanal.controller;

import com.example.semanal.entity.Item;
import com.example.semanal.entity.Pedido;
import com.example.semanal.repository.ItemRepository;
import com.example.semanal.repository.PedidoRepository;
import com.example.semanal.service.ItemService;
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
@RequestMapping(value = "api/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Item> findByIDPath(@PathVariable("id") final Long id) {
        final Item item = this.itemRepository.findById(id).orElse(null);
        return ResponseEntity.ok(item);
    }

    @GetMapping
    public ResponseEntity<List<Item>> listAll() {
        return ResponseEntity.ok(this.itemRepository.findAll());
    }

    @PostMapping
    public ResponseEntity <HttpStatus> creteItem(@RequestBody final Item item) {
        try {
            this.itemService.save(item);
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
