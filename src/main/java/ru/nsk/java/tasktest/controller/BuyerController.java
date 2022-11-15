package ru.nsk.java.tasktest.controller;

import lombok.Getter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsk.java.tasktest.entity.Buyer;
import ru.nsk.java.tasktest.repo.BuyerRepository;
import ru.nsk.java.tasktest.service.BuyerService;


import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@RestController
@RequestMapping("/buyer")
public class BuyerController {

    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @PostMapping("/id")
    public ResponseEntity<Buyer> findById(@RequestBody Long id) {

        Optional<Buyer> buyerOptional = buyerService.findById(id);

        try {
           if(buyerOptional.isPresent()){
               return ResponseEntity.ok(buyerOptional.get());
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
       return new ResponseEntity("id= " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/update")
    public ResponseEntity<Buyer> update(@RequestBody Buyer buyer) {

        if (buyer.getId() == null || buyer.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        if (buyer.getFirstName() == null || buyer.getFirstName().trim().length() == 0) {
            return new ResponseEntity("missed param: first name", HttpStatus.NOT_ACCEPTABLE);
        }

        if (buyer.getLastName() == null || buyer.getLastName().trim().length() == 0) {
            return new ResponseEntity("missed param: last name", HttpStatus.NOT_ACCEPTABLE);
        }


        buyerService.update(buyer);

        return new ResponseEntity(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {

        try {
            buyerService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id= " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Buyer> add(@RequestBody Buyer buyer) {


        if (buyer.getId() != null && buyer.getId() != 0) {

            return new ResponseEntity("redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }


        if (buyer.getFirstName() == null || buyer.getFirstName().trim().length() == 0) {
            return new ResponseEntity("missed param: first name", HttpStatus.NOT_ACCEPTABLE);
        }

        if (buyer.getLastName() == null || buyer.getLastName().trim().length() == 0) {
            return new ResponseEntity("missed param: last name", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(buyerService.add(buyer));
    }

    ///---------------------------------------------------------------------------------------------//

    @GetMapping("/findBuyerByProduct")
    public ResponseEntity<List<Buyer>> findBuyerByProduct(){
        return ResponseEntity.ok(buyerService.findBuyerByProduct(4L, "Кефир"));
    }

    @GetMapping("/findByLastName")
    public ResponseEntity<List<Buyer>> findByLastName(){
        return ResponseEntity.ok(buyerService.findByLastName("Давыдов"));
    }


    @GetMapping("/findMinMax")
    public ResponseEntity<List<Buyer>> findMaxMin(){
        return ResponseEntity.ok(buyerService.findMinMax(199L, 2001L));
    }



    @GetMapping("/findBad")
    public ResponseEntity<List<Buyer>> findBad(){
        return ResponseEntity.ok(buyerService.findBad(1));
    }

    @GetMapping("/buyersByDate")
    public ResponseEntity<Map<String, List<BuyerRepository.BuyerStat>>> buyersByDate() {
        Calendar dateFrom = Calendar.getInstance();
        dateFrom.add(Calendar.DAY_OF_MONTH, -15);

        Date dateTo = new Date();
        return ResponseEntity.ok(buyerService.buyersByDate(dateFrom.getTime(), dateTo));
    }
}