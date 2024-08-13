package com.fetch.controller;

import com.fetch.model.Receipt;
import com.fetch.service.ReceiptService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/receipts")
class ReceiptController {

    private final ReceiptService receiptService;

    ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    private record ProcessReceiptResponse(String id) {}

    @PostMapping("/process")
    private ResponseEntity<ProcessReceiptResponse> processReceipt(@Validated  @RequestBody Receipt receipt) {
        Receipt savedReceipt = receiptService.processAndSaveReceipt(receipt);
        return ResponseEntity.ok(new ProcessReceiptResponse(savedReceipt.getId()));
    }

    private record GetPointsResponse(Integer points) {}

    @GetMapping("/{id}/points")
    private ResponseEntity<GetPointsResponse> getPoints(@PathVariable("id") String id) {
        Optional<Receipt> receipt = receiptService.findById(id);
        if (receipt.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new GetPointsResponse(receipt.get().getPoints()));
        }
    }

}
