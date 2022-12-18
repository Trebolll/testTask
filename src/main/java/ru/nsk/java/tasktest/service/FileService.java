package ru.nsk.java.tasktest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsk.java.tasktest.json.search.SearchResult;
import ru.nsk.java.tasktest.json.stat.Stat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class FileService {
    // для ручной работы с JSON (в нашем случае будем использовать запись в файл, считывание)
    private ObjectMapper objectMapper = new ObjectMapper();

    private JsonService jsonService;


    @Autowired
    public FileService(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    // записываем JSON поиска в файл

    public void search(String inputFilePath, String outputFilePath) {
        try {

            // считываем JSON из файла
            LinkedHashMap<String, List<Object>> map = objectMapper.readValue(new File(inputFilePath), LinkedHashMap.class);

            // вызываем готовый сервис
            SearchResult searchResult = jsonService.searchJson(map);

            // запись итогового JSON в файл
            objectMapper.writeValue(new File(outputFilePath), searchResult);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // записываем JSON статистики в файл
    public void stat(String inputFilePath, String outputFilePath) {

        try {

            // считываем JSON из файла
            LinkedHashMap<String, String> object = objectMapper.readValue(new File(inputFilePath), LinkedHashMap.class);

            // получаем даты для статистики
            Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(object.get("startDate").toString());
            Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(object.get("endDate").toString());

            // вызываем готовый сервис
            Stat stat = jsonService.statJson(dateFrom, dateTo);

            // запись итогового JSON в файл
            objectMapper.writeValue(new File(outputFilePath), stat);


        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    }
