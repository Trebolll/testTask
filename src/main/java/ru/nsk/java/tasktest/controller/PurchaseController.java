package ru.nsk.java.tasktest.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsk.java.tasktest.entity.Purchase;
import ru.nsk.java.tasktest.service.PurchaseService;


import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/id")
    public ResponseEntity<Purchase> findById(@RequestBody Long id) {
        Optional<Purchase> purchaseOptional = purchaseService.findById(id);
        try {
           if(purchaseOptional.isPresent()){
               return ResponseEntity.ok(purchaseOptional.get());
           }
        } catch (NoSuchElementException e) {
            e.printStackTrace();

        }
        return new ResponseEntity("id= " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/update")
    public ResponseEntity<Purchase> update(@RequestBody Purchase purchase) {


        if (purchase.getId() == null || purchase.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        purchaseService.update(purchase);

        return new ResponseEntity(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {

        try {
            purchaseService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id= " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK); }

    @PostMapping("/add")
    public ResponseEntity<Purchase> add(@RequestBody Purchase purchase) {

        if (purchase.getId() != null && purchase.getId() != 0) {
            return new ResponseEntity("redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(purchaseService.add(purchase));
    }
    
}
