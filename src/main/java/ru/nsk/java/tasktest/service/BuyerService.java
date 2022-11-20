package ru.nsk.java.tasktest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.nsk.java.tasktest.entity.Buyer;
import ru.nsk.java.tasktest.json.Customer;
import ru.nsk.java.tasktest.json.PurchaseJson;
import ru.nsk.java.tasktest.json.Stat;
import ru.nsk.java.tasktest.repo.BuyerRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    public Buyer add(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    public Buyer update(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    public void deleteById(Long id) {
        buyerRepository.deleteById(id);
    }


    public List<Buyer> findByName(String name) {
        return buyerRepository.findByLastNameContainsIgnoreCase(name);
    }

    public List<Buyer> findBuyerByProduct(String productName, long minPuchases) {
        return buyerRepository.findBuyerByProduct(productName, minPuchases);
    }

    public List<Buyer> findMinMax(long min, long max) {
        return buyerRepository.findMinMax(min, max);
    }

    public List<Buyer> findBad(int count) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        PageRequest pageRequest = PageRequest.of(0, count, sort);

        return buyerRepository.findBad(pageRequest).getContent();
    }

    public Stat buyersByDate(Date dateFrom, Date dateTo) {
        ObjectMapper mapper = new ObjectMapper();
        List<BuyerRepository.BuyerStat> listBuyers = buyerRepository.buyersByDate(dateFrom, dateTo);
        Map<String, List<BuyerRepository.BuyerStat>> map = new HashMap<>();
        for (BuyerRepository.BuyerStat buyerStat : listBuyers) {
            List list;
            if (!map.containsKey(buyerStat.getBuyerName())) {

                list = new ArrayList();

            } else {
                list = map.get(buyerStat.getBuyerName());
            }

            list.add(buyerStat);

            map.put(buyerStat.getBuyerName(), list);

        }

        Stat stat = new Stat();
        List<Customer> customers = new ArrayList<>();
        stat.setCustomers(customers);


        long diffDates = Math.abs(dateFrom.getTime() - dateTo.getTime());
        long diffDays = TimeUnit.DAYS.convert(diffDates, TimeUnit.MILLISECONDS);
        stat.setTotalDays(diffDays);

        long totalExpensesAll = 0;


        for (Map.Entry<String, List<BuyerRepository.BuyerStat>> entry : map.entrySet()) {
            Customer customer = new Customer();
            customer.setName(entry.getKey());

            customers.add(customer);

            List<PurchaseJson> purchaseJsons = new ArrayList<>();

            long totalExpenses = 0;
            for (BuyerRepository.BuyerStat buyerStat : entry.getValue()) {
                PurchaseJson purchaseJson = new PurchaseJson();
                purchaseJson.setName(buyerStat.getProductName());
                purchaseJson.setExpenses(buyerStat.getExpenses());
                totalExpenses += buyerStat.getExpenses();
                purchaseJsons.add(purchaseJson);
            }

            customer.setTotalExpenses(totalExpenses);
            totalExpensesAll += totalExpenses;
            customer.setPurchases(purchaseJsons);

        }

        stat.setTotalExpenses(totalExpensesAll);

        double avgExpenses = (double)totalExpensesAll / customers.size();

        stat.setAvgExpenses(avgExpenses);


        try {
            mapper.writeValue(new File("D:\\Backend\\testTask\\src\\main\\resources\\output.json"),stat);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stat;

    }
}
