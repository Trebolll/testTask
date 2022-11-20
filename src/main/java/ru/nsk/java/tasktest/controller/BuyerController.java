package ru.nsk.java.tasktest.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsk.java.tasktest.entity.Buyer;
import ru.nsk.java.tasktest.json.BuyerResult;
import ru.nsk.java.tasktest.json.SearchResult;
import ru.nsk.java.tasktest.json.Stat;
import ru.nsk.java.tasktest.service.BuyerService;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/buyer")
public class BuyerController {

    private final BuyerService buyerService;



    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;

    }

//    @PostMapping("/id")
//    public ResponseEntity<Buyer> findById(@RequestBody Long id) {
//
//        Optional<Buyer> buyerOptional = buyerService.findById(id);
//
//        try {
//           if(buyerOptional.isPresent()){// если объект найден
//               return ResponseEntity.ok(buyerOptional.get());//получаем User из контейнера и возвращаем в теле ответа
//            }
//        } catch (NoSuchElementException e) {
//            e.printStackTrace();
//        }
//       return new ResponseEntity("id= " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<Buyer> update(@RequestBody Buyer buyer) {
//
//        // проверка на обязательные параметры
//        if (buyer.getId() == null || buyer.getId() == 0) {
//            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
//        }
//
//        // если передали пустое значение title
//        if (buyer.getFirstName() == null || buyer.getFirstName().trim().length() == 0) {
//            return new ResponseEntity("missed param: first name", HttpStatus.NOT_ACCEPTABLE);
//        }
//
//        if (buyer.getLastName() == null || buyer.getLastName().trim().length() == 0) {
//            return new ResponseEntity("missed param: last name", HttpStatus.NOT_ACCEPTABLE);
//        }
//
//        // save работает как на добавление, так и на обновление
//        buyerService.update(buyer);
//
//        return new ResponseEntity(HttpStatus.OK);
//    }
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity delete(@PathVariable("id") Long id) {
//
//        try {
//            buyerService.deleteById(id);
//        } catch (EmptyResultDataAccessException e) {
//            e.printStackTrace();
//            return new ResponseEntity("id= " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
//        }
//        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<Buyer> add(@RequestBody Buyer buyer) {
//
//        // проверка на обязательные параметры
//        if (buyer.getId() != null && buyer.getId() != 0) {
//            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
//            return new ResponseEntity("redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
//        }
//
//        // если передали пустое значение title
//        if (buyer.getFirstName() == null || buyer.getFirstName().trim().length() == 0) {
//            return new ResponseEntity("missed param: first name", HttpStatus.NOT_ACCEPTABLE);
//        }
//
//        if (buyer.getLastName() == null || buyer.getLastName().trim().length() == 0) {
//            return new ResponseEntity("missed param: last name", HttpStatus.NOT_ACCEPTABLE);
//        }
//
//        return ResponseEntity.ok(buyerService.add(buyer));
//
//    }
@GetMapping("/findBuyerByProduct")
public ResponseEntity<List<Buyer>> findBuyerByProduct() {
    return ResponseEntity.ok(buyerService.findBuyerByProduct("Минеральная вода",1));
}

    @GetMapping("/findNyName")
    public ResponseEntity<List<Buyer>> findByName() {
        return ResponseEntity.ok(buyerService.findByName("Иванов"));
    }


    @GetMapping("/findMinMax")
    public ResponseEntity<List<Buyer>> findMaxMin() {
        return ResponseEntity.ok(buyerService.findMinMax(0, 100));
    }

    @GetMapping("/findBad")
    public ResponseEntity<List<Buyer>> findBad() {
        return ResponseEntity.ok(buyerService.findBad(2));
    }

    @GetMapping("/buyersByDate")
    public ResponseEntity<Stat> buyersByDate() {
        Calendar dateFrom = Calendar.getInstance();
        dateFrom.add(Calendar.DAY_OF_MONTH, -15);

        Date dateTo = new Date();
        return ResponseEntity.ok(buyerService.buyersByDate(dateFrom.getTime(), dateTo));
    }

    @PostMapping("/manyjson")
    public ResponseEntity<SearchResult> manyjson(@RequestBody Object object) {

        SearchResult searchResult = new SearchResult();
        ObjectMapper mapper = new ObjectMapper();


        LinkedHashMap<String, List<Object>> map = (LinkedHashMap<String, List<Object>>) object;

        List<Object> criterias = map.get("criterias");

        List<Object> result = new ArrayList<>();

        for (Object criteria : criterias) {

            LinkedHashMap<String, Object> mapInner = (LinkedHashMap<String, Object>) criteria;

            BuyerResult criteriaResult = new BuyerResult();

            criteriaResult.setCriteria(criteria);


            if (mapInner.containsKey("lastName")) {
                criteriaResult.setResults(buyerService.findByName(mapInner.get("lastName").toString()));

            }
            else
            if (mapInner.containsKey("productName")) {
                criteriaResult.setResults(buyerService.findBuyerByProduct(
                        mapInner.get("productName").toString(),
                        new Long(mapInner.get("minTimes").toString())

                ));

            }
            else
            if (mapInner.containsKey("minExpenses")) {
                criteriaResult.setResults(buyerService.findMinMax(
                        new Long(mapInner.get("minExpenses").toString()),

                        new Long(mapInner.get("maxExpenses").toString())
                ));

            }

            else
            if (mapInner.containsKey("badCustomers")) {
                criteriaResult.setResults(buyerService.findBad(
                        new Integer(mapInner.get("badCustomers").toString())
                ));
             ;
            }
            searchResult.getResults().add(criteriaResult);
        }
        try {
            mapper.writeValue(new File("D:\\Backend\\testTask\\src\\main\\resources\\output.json"),searchResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(searchResult);
    }

}