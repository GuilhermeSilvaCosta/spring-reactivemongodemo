package com.guilherme.reactivemongodemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.guilherme.reactivemongodemo.collections.Product;
import com.guilherme.reactivemongodemo.controllers.ProductController;
import com.guilherme.reactivemongodemo.repos.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class ReactivemongodemoApplicationTests {

	@Autowired
	ProductController controller;

	@MockBean
	ProductRepository repo;

	@Test
	void testAddProduct() {
		Product product = new Product(null, "IPhone", "Its Cool", 1200d);
		Product savedProduct = new Product("abc123", "IPhone", "Its Cool", 1200d);
		when(repo.save(product)).thenReturn(Mono.just(savedProduct));

		StepVerifier.create(controller.addProduct(product))
			.assertNext(p->{
				assertNotNull(p);
				assertNotNull(p.getId());
				assertEquals(p, savedProduct);
			})
			.expectComplete()
			.verify();
		verify(repo).save(product);
	}

	@Test
	void testGetProducts() {
		Product product = new Product("abc123", "IPhone", "Its Cool", 1200d);
		when(repo.findAll()).thenReturn(Flux.just(product));

		StepVerifier.create(controller.getProducts())
			.assertNext(p->{
				assertNotNull(p);
				assertNotNull(p.getId());
				assertEquals(p, product);
			})
			.expectComplete()
			.verify();
		verify(repo).findAll();
	}
}
