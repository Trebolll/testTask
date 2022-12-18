package ru.nsk.java.tasktest.service;

import org.springframework.stereotype.Service;
import ru.nsk.java.tasktest.json.search.BuyerResult;
import ru.nsk.java.tasktest.json.search.SearchResult;
import ru.nsk.java.tasktest.json.stat.Customer;
import ru.nsk.java.tasktest.json.stat.Stat;
import ru.nsk.java.tasktest.repo.BuyerRepository;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class JsonService {

    private final BuyerService buyerService; // для выполнения запросов в БД

    public JsonService(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    public SearchResult searchJson(LinkedHashMap<String, List<Object>> map) {

        // формат итогового JSON
        SearchResult searchResult = new SearchResult();

        // из входящего JSON нужно считать все критерии, чтобы по каждому вызвать соотв. метод из сервиса
        List<Object> criterias = map.get("criterias");

        // проходим по каждому критерию и вызывам нужный запрос
        for (Object criteria : criterias) {

            // считываем каждый критерий из JSON, чтобы понять, какой запрос нужно будет выполнить
            LinkedHashMap<String, Object> criteriaMap = (LinkedHashMap<String, Object>) criteria;

            // встроенный объект JSON, который содержит критерий и коллекцию с результатами
            BuyerResult criteriaResult = new BuyerResult();
            criteriaResult.setCriteria(criteria); // первое поле внутреннего JSON

            // в зависимости от значения ключа - выбираем нужный метод из сервиса
            if (criteriaMap.containsKey("lastName")) {
                criteriaResult.setResults(buyerService.findByName(criteriaMap.get("lastName").toString()));
            } else if (criteriaMap.containsKey("productName")) {
                criteriaResult.setResults(buyerService.findBuyerByProduct(
                        criteriaMap.get("productName").toString(),
                        Long.valueOf(criteriaMap.get("minTimes").toString())
                ));
            } else if (criteriaMap.containsKey("minExpenses")) {
                criteriaResult.setResults(buyerService.findMinMax(
                        Long.valueOf(criteriaMap.get("minExpenses").toString()),
                        Long.valueOf(criteriaMap.get("maxExpenses").toString())
                ));
            } else if (criteriaMap.containsKey("badCustomers")) {
                criteriaResult.setResults(buyerService.findBad(
                        Integer.valueOf(criteriaMap.get("badCustomers").toString())
                ));
            }

            // записываем результат (коллекцию с покупателями)
            searchResult.getResults().add(criteriaResult); // второе поле внутреннего JSON

        }

        return searchResult; // итоговый JSON нужного формата, согласно ТЗ
    }

    public Stat statJson(Date dateFrom, Date dateTo) {

        // сначала собираем самую внутреннюю коллекцию - покупатели и их покупки
        List<BuyerRepository.BuyerStatJSON> listBuyers = buyerService.buyersByDate(dateFrom, dateTo);

        // ключ - имя покупателя, значение - список его покупок
        Map<String, List<BuyerRepository.BuyerStatJSON>> map = new HashMap<>();

        // заполнение map с покупателем и их списком покупок
        for (BuyerRepository.BuyerStatJSON purchase : listBuyers) {

            List list;

            // если добавляем покупку первый раз - создаем новую коллекцию
            if (!map.containsKey(purchase.getBuyerName())) {
                list = new ArrayList();
            } else { // если уже ранее добавляли покупки - исп. старую коллекцию (чтобы не затирать старые данные)
                list = map.get(purchase.getBuyerName());
            }

            list.add(purchase);

            // в конце должна получиться карта, где ключ - это имя пользователя, а значение - его покупки
            map.put(purchase.getBuyerName(), list);

        }

        // собираем общий JSON
        Stat stat = new Stat();
        List<Customer> customers = new ArrayList<>();
        stat.setCustomers(customers);

        // дни периода
        long diffDates = Math.abs(dateFrom.getTime() - dateTo.getTime());
        long diffDays = TimeUnit.DAYS.convert(diffDates, TimeUnit.MILLISECONDS);
        stat.setTotalDays(diffDays);

        // общая сумма всех товаров всех покупателей
        long totalExpensesAll = 0;


        // перебираем ранее созданный map и с помощью него формируем customers
        // собираем нужный JSON согласно формату ТЗ
        for (Map.Entry<String, List<BuyerRepository.BuyerStatJSON>> entry : map.entrySet()) {

            Customer customer = new Customer();
            customer.setName(entry.getKey()); // имя покупателя
            customer.setPurchases(entry.getValue()); // коллекция покупок

            // сумма всех покупок конкретного пользователя
            long cutomerTotalExpenses = 0;
            for (BuyerRepository.BuyerStatJSON purchase : entry.getValue()) {
                cutomerTotalExpenses += purchase.getExpenses();
            }

            // подсумма для конкретного покупателя (по всем его товарам)
            customer.setTotalExpenses(cutomerTotalExpenses);

            // общая сумма всех товаров всех покупателей
            totalExpensesAll += cutomerTotalExpenses;

            // добавляем в общую коллекцию customers
            customers.add(customer);

        }

        // общая сумма всех покупок
        stat.setTotalExpenses(totalExpensesAll);

        // среднее значение по всем покупкам (средний чек)
        double avgExpenses = 0;
        if (totalExpensesAll != 0 && !customers.isEmpty()) {
            avgExpenses = (double) totalExpensesAll / customers.size();
        }
        stat.setAvgExpenses(avgExpenses);

        return stat;

    }


}
