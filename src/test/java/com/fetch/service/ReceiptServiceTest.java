package com.fetch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fetch.model.Receipt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReceiptServiceTest {

    @Autowired
    private ReceiptService receiptService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void calculatePointsTest() throws Exception {
        int points = receiptService.calculatePoints(generateReceipt1());
        assertThat(points).isEqualTo(28);

        points = receiptService.calculatePoints(generateReceipt2());
        assertThat(points).isEqualTo(109);
    }

    private Receipt generateReceipt1() throws JsonProcessingException {
        String json = "{\n" +
                "  \"retailer\": \"Target\",\n" +
                "  \"purchaseDate\": \"2022-01-01\",\n" +
                "  \"purchaseTime\": \"13:01\",\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"shortDescription\": \"Mountain Dew 12PK\",\n" +
                "      \"price\": \"6.49\"\n" +
                "    },{\n" +
                "      \"shortDescription\": \"Emils Cheese Pizza\",\n" +
                "      \"price\": \"12.25\"\n" +
                "    },{\n" +
                "      \"shortDescription\": \"Knorr Creamy Chicken\",\n" +
                "      \"price\": \"1.26\"\n" +
                "    },{\n" +
                "      \"shortDescription\": \"Doritos Nacho Cheese\",\n" +
                "      \"price\": \"3.35\"\n" +
                "    },{\n" +
                "      \"shortDescription\": \"   Klarbrunn 12-PK 12 FL OZ  \",\n" +
                "      \"price\": \"12.00\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": \"35.35\"\n" +
                "}";
        return objectMapper.readValue(json, Receipt.class);
    }

    private Receipt generateReceipt2() throws JsonProcessingException {
        String json = "{\n" +
                "  \"retailer\": \"M&M Corner Market\",\n" +
                "  \"purchaseDate\": \"2022-03-20\",\n" +
                "  \"purchaseTime\": \"14:33\",\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"shortDescription\": \"Gatorade\",\n" +
                "      \"price\": \"2.25\"\n" +
                "    },{\n" +
                "      \"shortDescription\": \"Gatorade\",\n" +
                "      \"price\": \"2.25\"\n" +
                "    },{\n" +
                "      \"shortDescription\": \"Gatorade\",\n" +
                "      \"price\": \"2.25\"\n" +
                "    },{\n" +
                "      \"shortDescription\": \"Gatorade\",\n" +
                "      \"price\": \"2.25\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": \"9.00\"\n" +
                "}";
        return objectMapper.readValue(json, Receipt.class);
    }
}