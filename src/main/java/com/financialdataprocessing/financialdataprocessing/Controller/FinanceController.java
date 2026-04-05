package com.financialdataprocessing.financialdataprocessing.Controller;

import com.financialdataprocessing.financialdataprocessing.Model.Entity.FinancialRecord;
import com.financialdataprocessing.financialdataprocessing.Service.DashboardService;
import com.financialdataprocessing.financialdataprocessing.Service.FinancialRecordService;
import com.financialdataprocessing.financialdataprocessing.Service.UserService;
import com.financialdataprocessing.financialdataprocessing.dto.DashboardSummaryDTO;
import com.financialdataprocessing.financialdataprocessing.dto.FinancialRecordRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/finance")
@RequiredArgsConstructor
public class FinanceController {

    private final DashboardService dashboardService;
    private final FinancialRecordService financialRecordService;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDTO> getSummary(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByUsername(userDetails.getUsername());
        return ResponseEntity.ok(dashboardService.getSummary(userId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @PostMapping("/records")
    public ResponseEntity<?> addRecord(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String type,
            @Valid @RequestBody FinancialRecordRequestDTO request) {

        Long userId = userService.getUserIdByUsername(userDetails.getUsername());
        financialRecordService.saveRecord(userId, request, type);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    @GetMapping("/records")
    public ResponseEntity<List<FinancialRecord>> getRecords(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category) {

        Long userId = userService.getUserIdByUsername(userDetails.getUsername());

        if (type != null) {
            return ResponseEntity.ok(financialRecordService.getRecordsByType(userId, type));
        }
        if (category != null) {
            return ResponseEntity.ok(financialRecordService.getRecordsByCategory(userId, category));
        }
        return ResponseEntity.ok(financialRecordService.getAllRecords(userId));
    }
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @PutMapping("/records/{recordId}")
    public ResponseEntity<FinancialRecord> updateRecord(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long recordId,
            @Valid @RequestBody FinancialRecordRequestDTO request){
        Long userId=userService.getUserIdByUsername(userDetails.getUsername());
        FinancialRecord updated=financialRecordService.updateRecord(userId,recordId,request);
        return ResponseEntity.ok(updated);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/records/{recordId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long recordId
    ){
       Long userId=userService.getUserIdByUsername(userDetails.getUsername());
         financialRecordService.deleteRecord(userId,recordId);
         return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    @GetMapping("/records/search")
    public ResponseEntity<Page<FinancialRecord>> searchRecords(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String keyword,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size
    ){
        Long userId=userService.getUserIdByUsername(userDetails.getUsername());
        Pageable pageable= PageRequest.of(page,size, Sort.by("entryDate").descending());
        return ResponseEntity.ok(financialRecordService.search(userId, keyword, pageable));
    }

}
