package ru.nsk.java.tasktest.service;

import org.springframework.stereotype.Service;
import ru.nsk.java.tasktest.entity.Buyer;
import ru.nsk.java.tasktest.entity.Product;
import ru.nsk.java.tasktest.repo.ProductRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findById(Long id) {
        return productRepository.findById(id).get();
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
