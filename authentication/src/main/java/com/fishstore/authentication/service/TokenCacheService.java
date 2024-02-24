package com.fishstore.authentication.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

//@Component
@Service
public class TokenCacheService {
    private final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();

    public void store(String token, Object data) {
        cache.put(token, data);
    }

    public Object retrieve(String token) {
        return cache.get(token);
    }

    public void remove(String token) {
        cache.remove(token);
    }

    public boolean containsToken(String token) {
        return cache.containsKey(token);
    }
}
