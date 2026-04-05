package com.financialdataprocessing.financialdataprocessing.Controller;

import com.financialdataprocessing.financialdataprocessing.Model.Entity.User;
import com.financialdataprocessing.financialdataprocessing.Model.Enum.UserStatus;
import com.financialdataprocessing.financialdataprocessing.Service.UserService;
import com.financialdataprocessing.financialdataprocessing.dto.UserRegistrationRequest;
import com.financialdataprocessing.financialdataprocessing.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegistrationRequest request){
        User user = userService.registerUser(request);
        return ResponseEntity.ok(UserResponseDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{userId}/status")
    public ResponseEntity<UserResponseDTO> changeStatus(
            @PathVariable Long userId,
            @RequestParam UserStatus status){
        return ResponseEntity.ok(userService.updateUserStatus(userId, status));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all") // Changed to /all for clarity, or just keep as base path
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
}   

