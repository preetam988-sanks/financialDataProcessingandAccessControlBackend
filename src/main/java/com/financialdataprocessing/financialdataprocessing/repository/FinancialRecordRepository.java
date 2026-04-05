package com.financialdataprocessing.financialdataprocessing.repository;

import com.financialdataprocessing.financialdataprocessing.Model.Entity.FinancialRecord;
import com.financialdataprocessing.financialdataprocessing.Model.Enum.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {
    List<FinancialRecord> findAllByUserIdAndDeletedFalse(Long userId);
    List<FinancialRecord> findByUserIdAndTypeAndDeletedFalse(Long userId, TransactionType type);
    List<FinancialRecord>findByUserIdAndDeletedFalse(Long userId);
    Page<FinancialRecord> findAllByUserIdAndDeletedFalse(Long userId, Pageable pageable);
    Page<FinancialRecord> findByUserIdAndDescriptionContainingIgnoreCaseAndDeletedFalse(
            Long userId,String keyword,Pageable pageable
    );
}
