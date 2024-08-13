package com.fetch.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Receipt {

    @Setter
    @Null
    private String id;

    @NotBlank
    private String retailer;

    @NotBlank
    private String purchaseDate;

    @NotBlank
    private String purchaseTime;

    @NotBlank
    private String total;

    @Valid
    @NotEmpty
    private List<Item> items;

    @Setter
    @Null
    private Integer points;
}
