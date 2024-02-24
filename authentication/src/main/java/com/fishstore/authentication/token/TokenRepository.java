package com.fishstore.authentication.token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, UUID> {

    List<Token> findAllValidTokenByUserId(UUID id);

    Optional<Token> findByToken(String token);
}
