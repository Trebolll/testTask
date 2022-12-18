package ru.nsk.java.tasktest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



//@SpringBootApplication
public class TaskTestApplication  {

    public static void main(String[] args) {
        SpringApplication.run(TaskTestApplication.class, args);

    }

}

