package com.fetch.dao;

import com.fetch.model.Receipt;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReceiptRepositoryTest {

    private ReceiptRepository receiptRepository = new ReceiptRepository();

    @Test
    void saveAndFind() {
        Receipt receipt = new Receipt();
        receipt.setPoints(30);
        Receipt saved = receiptRepository.save(receipt);
        Optional<Receipt> retrieved = receiptRepository.findById(saved.getId());
        assertThat(retrieved).isNotEmpty();
        assertThat(retrieved.get().getPoints()).isEqualTo(30);

    }

    @Test
    void findNonexistentId() {
        Optional<Receipt> receipt = receiptRepository.findById("testing");
        assertThat(receipt).isEmpty();
    }
}