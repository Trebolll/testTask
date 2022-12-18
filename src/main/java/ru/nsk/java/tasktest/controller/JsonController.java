package ru.nsk.java.tasktest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsk.java.tasktest.json.search.SearchResult;
import ru.nsk.java.tasktest.json.stat.Stat;
import ru.nsk.java.tasktest.search.FindBuyerByDate;
import ru.nsk.java.tasktest.service.JsonService;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/json")
public class JsonController {

    private final JsonService jsonService; // сервис для формирования JSON'ов

    public JsonController(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    // Собирает итоговый JSON из параметров запросов входящего JSON (как конструктор)
    @PostMapping("/searchWithJson")
    public ResponseEntity<SearchResult> search(@RequestBody Object object) {

        // приведение объекта входяшего JSON в его фактический формат
        LinkedHashMap<String, List<Object>> map = (LinkedHashMap<String, List<Object>>) object;
        return ResponseEntity.ok(jsonService.searchJson(map));

    }

    // Собирает статистику в итоговый JSON - по всем покупателям и датам
    @PostMapping("/buyerStatJson")
    public ResponseEntity<Stat> stat(@RequestBody FindBuyerByDate dates) {
        return ResponseEntity.ok(jsonService.statJson(dates.getDateFrom(), dates.getDateTo()));
    }



}
