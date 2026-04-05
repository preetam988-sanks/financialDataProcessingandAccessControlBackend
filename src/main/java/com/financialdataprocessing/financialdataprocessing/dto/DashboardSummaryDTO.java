package com.financialdataprocessing.financialdataprocessing.dto;

import com.financialdataprocessing.financialdataprocessing.Model.Entity.FinancialRecord;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardSummaryDTO {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netBalance;
    private Map<String, BigDecimal> incomeByCategory;
    private List<FinancialRecord> finacialRecordList;
    private Map<String,BigDecimal> monthlyTrend;
}
