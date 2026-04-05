package com.financialdataprocessing.financialdataprocessing.repository;

import com.financialdataprocessing.financialdataprocessing.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByUserName(String userName);
}
