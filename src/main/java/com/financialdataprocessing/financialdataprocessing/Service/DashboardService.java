package com.financialdataprocessing.financialdataprocessing.Service;

import com.financialdataprocessing.financialdataprocessing.Model.Entity.FinancialRecord;
import com.financialdataprocessing.financialdataprocessing.Model.Enum.TransactionType;
import com.financialdataprocessing.financialdataprocessing.dto.DashboardSummaryDTO;
import com.financialdataprocessing.financialdataprocessing.repository.FinancialRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class DashboardService {

    private final FinancialRecordRepository financialRecordRepository;
    @Autowired
    public DashboardService(FinancialRecordRepository financialRecordRepository) {
        this.financialRecordRepository = financialRecordRepository;
    }
    public DashboardSummaryDTO getSummary(Long userId){
        List<FinancialRecord> records=financialRecordRepository.findAllByUserIdAndDeletedFalse(userId);
        BigDecimal income=records.stream()
                .filter(r->r.getType()== TransactionType.INCOME)
                .map(FinancialRecord::getAmount)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal expense=records.stream()
                .filter(r->r.getType()== TransactionType.EXPENSE)
                .map(FinancialRecord::getAmount)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        Map<String,BigDecimal> categoryMap=records.stream()
                .collect(Collectors.groupingBy(
                        FinancialRecord :: getCategory,
                        Collectors.mapping(FinancialRecord::getAmount,Collectors.reducing(BigDecimal.ZERO,BigDecimal::add))

                ));
        List<FinancialRecord> recent=records.stream()
                .sorted(Comparator.comparing(FinancialRecord::getEntryDate).reversed())
                .limit(5)
                .collect(Collectors.toList());
        Map<String,BigDecimal> monthlyTrend=records.stream()
                .collect(Collectors.groupingBy(
                         r->r.getEntryDate().getMonth().name()+"-"+r.getEntryDate().getYear(),
                        Collectors.mapping(FinancialRecord::getAmount,Collectors.reducing(BigDecimal.ZERO,BigDecimal::add))
                ));

        return DashboardSummaryDTO.builder()
                .totalIncome(income)
                .totalExpense(expense)
                .netBalance(income.subtract(expense))
                .incomeByCategory(categoryMap)
                .finacialRecordList(recent)
                .monthlyTrend(monthlyTrend)
                .build();
    }
}
