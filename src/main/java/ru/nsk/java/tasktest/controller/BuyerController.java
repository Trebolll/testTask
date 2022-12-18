package ru.nsk.java.tasktest.controller;


import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import ru.nsk.java.tasktest.entity.Buyer;
import ru.nsk.java.tasktest.repo.BuyerRepository;
import ru.nsk.java.tasktest.search.FindBuyerByMinMax;
import ru.nsk.java.tasktest.search.FindBuyerByProduct;
import ru.nsk.java.tasktest.search.FindBuyerByDate;
import ru.nsk.java.tasktest.service.BuyerService;


import java.util.*;

@RestController
@RequestMapping("/buyer")
public class BuyerController {

    private final BuyerService buyerService; // сервис для обращений к БД

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }


    // Поиск покупателей по фамилии
    @PostMapping("/findByName")
    public ResponseEntity<List<Buyer>> findByName(@RequestBody String text) {
        return ResponseEntity.ok(buyerService.findByName(text));
    }

    // Поиск покупателей, купивших товар не менее, чем указанное число раз
    @PostMapping("/findByProduct")
    public ResponseEntity<List<Buyer>> findBuyerByProduct(@RequestBody FindBuyerByProduct searchFields) {
        return ResponseEntity.ok(buyerService.findBuyerByProduct(searchFields.getProductName(), searchFields.getMinPurchases()));
    }

    // Поиск покупателей, у которых общая стоимость всех покупок за всё время попадает в интервал (>мин, <макс)
    @PostMapping("/findMinMax")
    public ResponseEntity<List<Buyer>> findMaxMin(@RequestBody FindBuyerByMinMax searchFields) {
        return ResponseEntity.ok(buyerService.findMinMax(searchFields.getMinSum(), searchFields.getMaxSum()));
    }


    // Поиск покупателей, купивших меньше всего товаров. Возвращается не более, чем указанное число покупателей.
    @PostMapping("/findBad")
    public ResponseEntity<List<Buyer>> findBad(@RequestBody Integer limit) {
        return ResponseEntity.ok(buyerService.findBad(limit));
    }

    // Cтатистика по покупателям за период (кто чего и сколько купил в сумме)
    @PostMapping("/buyerStat")
    public ResponseEntity<List<BuyerRepository.BuyerStatJSON>> buyersByDate(@RequestBody FindBuyerByDate buyerByDates) {
        return ResponseEntity.ok(buyerService.buyersByDate(buyerByDates.getDateFrom(), buyerByDates.getDateTo()));
    }
}