package ru.nsk.java.tasktest.service;

import org.springframework.stereotype.Service;
import ru.nsk.java.tasktest.entity.Product;
import ru.nsk.java.tasktest.repo.ProductRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product add(Product product) {
        return productRepository.save(product);
    }

    public Product update(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
