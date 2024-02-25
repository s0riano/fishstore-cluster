package com.fishstore.security.token;

import com.fishstore.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public Token createToken(User user, String jwtToken, boolean revoked, boolean expired) {
        String tokenId = UUID.randomUUID().toString();
        UUID uuid = UUID.fromString(tokenId);

        Token token = Token.builder()
                .id(uuid)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(revoked)
                .expired(expired)
                .user(user)
                .build();

        if (token.getTokenType() == null) {
            throw new IllegalArgumentException("Error setting TokenType. \\nTokenType: " + token.getToken());
        }

        try {
            tokenRepository.save(token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }
}