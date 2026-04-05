package com.financialdataprocessing.financialdataprocessing.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter@Setter
public class FinancialRecordRequestDTO {
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    @NotBlank(message = "Category is required")
    private String category;
    @NotNull(message="Date is required")
    private LocalDate entryDate;
    private String description;

}

