package ru.nsk.java.tasktest.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsk.java.tasktest.entity.Buyer;
import ru.nsk.java.tasktest.service.BuyerService;


import java.util.NoSuchElementException;
import java.util.Optional;

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
           if(buyerOptional.isPresent()){// если объект найден
               return ResponseEntity.ok(buyerOptional.get());//получаем User из контейнера и возвращаем в теле ответа
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
       return new ResponseEntity("id= " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/update")
    public ResponseEntity<Buyer> update(@RequestBody Buyer buyer) {

        // проверка на обязательные параметры
        if (buyer.getId() == null || buyer.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (buyer.getFirstName() == null || buyer.getFirstName().trim().length() == 0) {
            return new ResponseEntity("missed param: first name", HttpStatus.NOT_ACCEPTABLE);
        }

        if (buyer.getLastName() == null || buyer.getLastName().trim().length() == 0) {
            return new ResponseEntity("missed param: last name", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
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
        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }

    @PostMapping("/add")
    public ResponseEntity<Buyer> add(@RequestBody Buyer buyer) {

        // проверка на обязательные параметры
        if (buyer.getId() != null && buyer.getId() != 0) {
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (buyer.getFirstName() == null || buyer.getFirstName().trim().length() == 0) {
            return new ResponseEntity("missed param: first name", HttpStatus.NOT_ACCEPTABLE);
        }

        if (buyer.getLastName() == null || buyer.getLastName().trim().length() == 0) {
            return new ResponseEntity("missed param: last name", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(buyerService.add(buyer));

    }

}
