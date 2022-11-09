package ru.nsk.java.tasktest.service;

import org.springframework.stereotype.Service;
import ru.nsk.java.tasktest.entity.Buyer;
import ru.nsk.java.tasktest.repo.BuyerRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class BuyerService {

    private final BuyerRepository buyerRepository;

    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    public Buyer findById(Long id) {
        return buyerRepository.findById(id).get();
    }
    public Buyer add(Buyer buyer)  {
        return buyerRepository.save(buyer);
    }
    public Buyer update(Buyer buyer)  {
        return buyerRepository.save(buyer);
    }
    public void deleteById(Long id)  {
        buyerRepository.deleteById(id);
    }
}
