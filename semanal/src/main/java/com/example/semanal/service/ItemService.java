package com.example.semanal.service;

import com.example.semanal.entity.Item;
import com.example.semanal.entity.Pedido;
import com.example.semanal.repository.ItemRepository;
import com.example.semanal.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public Item save(Item item) {
        return itemRepository.save(item);
    }
}
