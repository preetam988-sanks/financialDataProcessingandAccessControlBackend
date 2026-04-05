package com.financialdataprocessing.financialdataprocessing.Service;

import com.financialdataprocessing.financialdataprocessing.Model.Entity.User;
import com.financialdataprocessing.financialdataprocessing.Model.Enum.UserRole;
import com.financialdataprocessing.financialdataprocessing.Model.Enum.UserStatus;
import com.financialdataprocessing.financialdataprocessing.dto.UserRegistrationRequest;
import com.financialdataprocessing.financialdataprocessing.dto.UserResponseDTO;
import com.financialdataprocessing.financialdataprocessing.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserRegistrationRequest registrationRequest){
        User user=new User();
        user.setUserName(registrationRequest.getUserName());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));        user.setEmail(registrationRequest.getEmail());
        user.setRole(UserRole.valueOf(registrationRequest.getRole()));
        user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }
    @Transactional
    public UserResponseDTO updateUserStatus(Long userId,UserStatus status){
        User user=userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User Not Found"));
        user.setStatus(status);
        User updateUser=userRepository.save(user);
        return mapToResponse(updateUser);
    }
    private UserResponseDTO mapToResponse(User user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
    public Long getUserIdByUsername(String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username))
                .getId();
    }
}
