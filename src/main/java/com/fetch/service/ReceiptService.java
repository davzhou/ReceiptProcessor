package com.fetch.service;

import com.fetch.dao.ReceiptRepository;
import com.fetch.model.Item;
import com.fetch.model.Receipt;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Component
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public Receipt processAndSaveReceipt(Receipt receipt) {
        receipt.setPoints(calculatePoints(receipt));
        return receiptRepository.save(receipt);
    }

    public Optional<Receipt> findById(String id) {
        return receiptRepository.findById(id);
    }

    public int calculatePoints(Receipt receipt) {
        int points = 0;
        for (Character c : receipt.getRetailer().toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                points++;
            }
        }

        double total = Double.valueOf(receipt.getTotal());
        if (total == Math.floor(total)) {
            points += 50;
        }

        double tolerance = 1E-10;
        if (total % .25 < tolerance) {
            points += 25;
        }

        points += receipt.getItems().size() / 2 * 5;

        for (Item item : receipt.getItems()) {
            if (item.shortDescription().trim().length() % 3 == 0) {
                points += Math.ceil(Double.valueOf(item.price()) * .2);
            }
        }

        LocalDate date = LocalDate.parse(receipt.getPurchaseDate());
        if (date.getDayOfMonth() % 2 == 1) {
            points += 6;
        }

        LocalTime time = LocalTime.parse(receipt.getPurchaseTime());
        int hour = time.getHour();
        if (hour >= 14 && hour < 16) {
            points += 10;
        }

        return points;
    }
}
