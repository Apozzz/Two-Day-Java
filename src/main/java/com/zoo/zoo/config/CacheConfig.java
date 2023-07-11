package com.zoo.zoo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zoo.zoo.animal.Animal;
import com.zoo.zoo.enclosure.Enclosure;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<Enclosure, List<Animal>> animalCache() {
        return Caffeine.newBuilder().build();
    }
}
