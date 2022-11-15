package ru.nsk.java.tasktest.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.nsk.java.tasktest.entity.Buyer;
import ru.nsk.java.tasktest.repo.BuyerRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BuyerService {

    private final BuyerRepository buyerRepository;

    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    public Optional<Buyer> findById(Long id) {
        return buyerRepository.findById(id);
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



    public List<Buyer> findByLastName(String lastName) {
        return buyerRepository.findByLastName(lastName);
    }

    public List<Buyer> findBuyerByProduct(Long minPurchase, String productName) {
        return buyerRepository.findBuyerByProduct(minPurchase, productName);
    }

    public List<Buyer> findMinMax(Long min, Long max) {
        return buyerRepository.findMinMax(min, max);
    }

    public List<Buyer> findBad(int count) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        PageRequest pageRequest = PageRequest.of(0, count, sort);

        return buyerRepository.findBad(pageRequest).getContent();
    }

}