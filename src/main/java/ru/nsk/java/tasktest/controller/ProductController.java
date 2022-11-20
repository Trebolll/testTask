package ru.nsk.java.tasktest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsk.java.tasktest.entity.Product;
import ru.nsk.java.tasktest.service.ProductService;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/id")
    public ResponseEntity<Product> findById(@RequestBody Long id) {
        Optional<Product> productOptional = productService.findById(id);
        try {
            if(productOptional.isPresent()){
                return ResponseEntity.ok(productOptional.get());
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> update(@RequestBody Product product) {
        if (product.getId() == null || product.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        if (product.getName() == null || product.getName().trim().length() == 0) {
            return new ResponseEntity("missed param: name", HttpStatus.NOT_ACCEPTABLE);
        }
        if (product.getCost() == null || product.getCost() == 0) {
            return new ResponseEntity("missed param: cost", HttpStatus.NOT_ACCEPTABLE);
        }
        productService.update(product);
        return new ResponseEntity(HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {

        try {
            productService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> add(@RequestBody Product product) {

        if (product.getId() != null && product.getId() != 0) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (product.getName() == null || product.getName().trim().length() == 0) {
            return new ResponseEntity("missed param: Name", HttpStatus.NOT_ACCEPTABLE);
        }

        if (product.getCost() == null || product.getCost() == 0) {
            return new ResponseEntity("missed param: cost", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(productService.add(product));

    }
    
}
