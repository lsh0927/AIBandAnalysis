package org.example.band.repository;

import java.util.Optional;

import org.example.band.entity.User;
import org.example.band.enums.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findFirstByEmailAndProvider(String email, Provider provider);
}