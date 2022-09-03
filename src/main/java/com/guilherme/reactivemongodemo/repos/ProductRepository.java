package com.guilherme.reactivemongodemo.repos;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.guilherme.reactivemongodemo.collections.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    
}
