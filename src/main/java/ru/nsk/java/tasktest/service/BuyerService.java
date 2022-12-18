package ru.nsk.java.tasktest.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.nsk.java.tasktest.entity.Buyer;
import ru.nsk.java.tasktest.repo.BuyerRepository;
import javax.transaction.Transactional;
import java.util.*;


@Service
@Transactional
public class BuyerService {

    private final BuyerRepository buyerRepository;

    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    // CRUD операции тоже оставим, хотя они по условию задачи не нужны
    public Optional<Buyer> findById(Long id) {
        return buyerRepository.findById(id);
    }
    public Buyer add(Buyer buyer) {
        return buyerRepository.save(buyer);
    }
    public Buyer update(Buyer buyer) {
        return buyerRepository.save(buyer);
    }
    public void deleteById(Long id) {
        buyerRepository.deleteById(id);
    }



    // Поиск покупателей по фамилии
    public List<Buyer> findByName(String name) {
        return buyerRepository.findByLastNameContainsIgnoreCase(name);
    }


    // Поиск покупателей, купивших товар не менее, чем указанное число раз
    public List<Buyer> findBuyerByProduct(String productName, long minPurchases) {
        return buyerRepository.findBuyerByProduct(productName, minPurchases);
    }

    // Поиск покупателей, у которых общая стоимость всех покупок за всё время попадает в интервал
    public List<Buyer> findMinMax(long min, long max) {
        return buyerRepository.findMinMax(min, max);
    }

    // Поиск покупателей, купивших меньше всего товаров. Возвращается не более, чем указанное число покупателей.
    public List<Buyer> findBad(int count) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(0, count, sort);
        return buyerRepository.findBad(pageRequest).getContent();
    }

    // Cтатистика по покупателям за период (кто чего и сколько купил в сумме)
    public List<BuyerRepository.BuyerStatJSON> buyersByDate(Date dateFrom, Date dateTo) {
        return buyerRepository.buyersByDate(dateFrom, dateTo);
    }
}
