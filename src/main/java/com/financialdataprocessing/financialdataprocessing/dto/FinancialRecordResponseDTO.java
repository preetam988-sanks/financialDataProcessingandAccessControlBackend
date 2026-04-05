package com.financialdataprocessing.financialdataprocessing.dto;

import com.financialdataprocessing.financialdataprocessing.Model.Enum.TransactionType;

import java.time.LocalDate;

public class FinancialRecordResponseDTO {
    private Long id;
    private String category;
    private TransactionType type;
    private LocalDate EntryDate;
    private String description;
    private Long userId;
}
