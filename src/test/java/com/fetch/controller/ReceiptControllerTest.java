package com.fetch.controller;

import com.fetch.dao.ReceiptRepository;
import com.fetch.service.ReceiptService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReceiptController.class)
@Import({ReceiptService.class, ReceiptRepository.class})
class ReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void processReceiptAndGetPoints() throws Exception {

        MvcResult result = mockMvc.perform(post("/receipts/process")
                        .content(jsonReceipt)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        String id = JsonPath.read(json, "$.id");

        mockMvc.perform(get("/receipts/{id}/points", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(28));
    }

    @Test
    public void processInvalidReceipt() throws Exception {
        mockMvc.perform(post("/receipts/process")
                        .content(jsonInvalidReceipt1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/receipts/process")
                        .content(jsonInvalidReceipt2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getNonexistentReceipt() throws Exception {
        mockMvc.perform(get("/receipts/{id}/points", "abc-def"))
                .andExpect(status().isNotFound());
    }

    private String jsonReceipt = "{\n" +
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

    private String jsonInvalidReceipt1 = "{\n" +
            "  \"retailer\": \"Target\",\n" +
            "  \"purchaseDate\": \"2022-01-01\",\n" +
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

    private String jsonInvalidReceipt2 = "{\n" +
            "  \"retailer\": \"Target\",\n" +
            "  \"purchaseDate\": \"2022-01-01\",\n" +
            "  \"items\": []\n" +
            "  \"total\": \"35.35\"\n" +
            "}";
}