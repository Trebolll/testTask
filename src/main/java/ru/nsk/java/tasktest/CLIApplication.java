package ru.nsk.java.tasktest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.nsk.java.tasktest.service.FileService;

@SpringBootApplication
public class CLIApplication implements CommandLineRunner
{

    private static ApplicationContext context;

    @Autowired
    public void context(ApplicationContext context) { this.context = context; }

    public static void main(String[] args) {
        SpringApplication.run(CLIApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // если не хватает параметров
        if (args.length<3){
            throw new Exception("must be 3 args");
        }

        String operation = args[0]; // какую операцию нужно выполнить (search, stat)
        String inputFilePath = args[1]; // откуда считать входящий JSON
        String outputFilePath = args[2]; // имя файла итогового JSON

        // Получить ссылку на Spring бин
        FileService fileService = context.getBean(FileService.class);

        switch (operation){
            case "search": {
                fileService.search(inputFilePath,outputFilePath);
                break;
            }

            case "stat":{
                fileService.stat(inputFilePath,outputFilePath);
                break;
            }
        }

    }
}