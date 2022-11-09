package ru.nsk.java.tasktest.service;

import org.springframework.stereotype.Service;
import ru.nsk.java.tasktest.entity.Purchase;
import ru.nsk.java.tasktest.repo.PurchaseRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class PurchaseService {

    private final PurchaseRepository productRepository;

    public PurchaseService(PurchaseRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Purchase findById(Long id) {
        return productRepository.findById(id).get();
    }

    public Purchase add(Purchase purchase) {
        return productRepository.save(purchase);
    }

    public Purchase update(Purchase purchase) {
        return productRepository.save(purchase);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
