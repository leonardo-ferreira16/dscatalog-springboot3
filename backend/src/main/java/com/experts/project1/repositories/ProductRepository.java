package com.experts.project1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.experts.project1.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
