package com.fetch.model;

import jakarta.validation.constraints.NotBlank;

public record Item(@NotBlank String shortDescription,
                   @NotBlank String price) {
}
