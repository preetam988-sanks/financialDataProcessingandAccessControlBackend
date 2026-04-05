package com.financialdataprocessing.financialdataprocessing.Service;

import com.financialdataprocessing.financialdataprocessing.Model.Entity.FinancialRecord;
import com.financialdataprocessing.financialdataprocessing.Model.Entity.User;
import com.financialdataprocessing.financialdataprocessing.Model.Enum.TransactionType;
import com.financialdataprocessing.financialdataprocessing.dto.FinancialRecordRequestDTO;
import com.financialdataprocessing.financialdataprocessing.repository.FinancialRecordRepository;
import com.financialdataprocessing.financialdataprocessing.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialRecordService {
    private final FinancialRecordRepository financialRecordRepository;
    private final UserRepository userRepository;

    @Transactional
    public FinancialRecord saveRecord(Long userId, FinancialRecordRequestDTO request, String type) {
              User user=userRepository.findById(userId)
                        .orElseThrow(()->new RuntimeException("User not found with id: "+userId));
        FinancialRecord record=FinancialRecord.builder()
                .amount(request.getAmount())
                .category(request.getCategory())
                .type(TransactionType.valueOf(type.toUpperCase()))    
                .entryDate(request.getEntryDate())
                .description(request.getDescription())
                .user(user)
                .build();
        return financialRecordRepository.save(record);
    }
    public List<FinancialRecord> getAllRecords(Long userId){
        return financialRecordRepository.findAllByUserIdAndDeletedFalse(userId);
    }
    public List<FinancialRecord> getRecordsByType(Long userId,String type){
        TransactionType transactionType=TransactionType.valueOf(type.toUpperCase());
        return financialRecordRepository.findByUserIdAndTypeAndDeletedFalse(userId,transactionType);
    }
    public List<FinancialRecord> getRecordsByCategory(Long userId,String category){
        return financialRecordRepository.findByUserIdAndDeletedFalse(userId).stream()
                .filter(r->r.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @Transactional
    public FinancialRecord updateRecord(Long userId,Long recordId,@Valid FinancialRecordRequestDTO request){
        FinancialRecord record =financialRecordRepository.findById(recordId)
                .orElseThrow(()->new RuntimeException("Record not found with id: "+recordId));
        if (!record.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You do not own this record.");
        }

        record.setAmount(request.getAmount());
        record.setCategory(request.getCategory());
        record.setEntryDate(request.getEntryDate());
        record.setDescription(request.getDescription());

        return financialRecordRepository.save(record);
    }
//    @Transactional
//    public void deleteRecord(Long userId,Long recordId) {
//        FinancialRecord record = financialRecordRepository.findById(recordId)
//                .orElseThrow(() -> new RuntimeException("Record not found with id: " + recordId));
//        if (!record.getUser().getId().equals(userId)) {
//            throw new RuntimeException("Unauthorized: You cannot delete somone else record.");
//
//        }
//        financialRecordRepository.delete(record);
//    }
    @Transactional
    public void deleteRecord(Long userId,Long recordId){
        FinancialRecord record=financialRecordRepository.findById(recordId)
                .orElseThrow(()->new RuntimeException("Record not found with id: "+recordId));
        if(!record.getUser().getId().equals(userId)){
            throw new RuntimeException("Unauthorized: You cannot delete someone else's record.");
        }
        record.setDeleted(true);
        financialRecordRepository.save(record);
    }
    @Transactional(readOnly = true)
    public Page<FinancialRecord> search(Long userId, String keyword, Pageable pageable){
        return financialRecordRepository.findByUserIdAndDescriptionContainingIgnoreCaseAndDeletedFalse(userId,keyword,pageable);
    }
}