package com.fetch.dao;

import com.fetch.model.Receipt;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class ReceiptRepository {

    private final Map<String, Receipt> store;

    public ReceiptRepository() {
        store = new HashMap<>();
    }

    public Receipt save(Receipt entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
        store.put(entity.getId(), entity);
        return entity;
    }

    public Optional<Receipt> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
}
