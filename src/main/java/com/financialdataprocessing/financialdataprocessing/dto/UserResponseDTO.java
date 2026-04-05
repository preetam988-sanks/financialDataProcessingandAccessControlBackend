package com.financialdataprocessing.financialdataprocessing.dto;

import com.financialdataprocessing.financialdataprocessing.Model.Enum.UserRole;
import com.financialdataprocessing.financialdataprocessing.Model.Enum.UserStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String userName;
    private String email;
    private UserRole role;
    private UserStatus status;

}
