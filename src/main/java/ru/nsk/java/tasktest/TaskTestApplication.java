package ru.nsk.java.tasktest;


import lombok.Value;
import lombok.extern.log4j.Log4j2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

@SpringBootApplication
public class TaskTestApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory
            .getLogger(TaskTestApplication.class);

    Path file;

    {
        try {
            file = ResourceUtils.getFile("src/main/resources/data.json").toPath();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(file);

    }


    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(TaskTestApplication.class, args);

        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        LOG.info("EXECUTING : command line runner");

        for (int i = 0; i < args.length; ++i) {
            LOG.info("args[{}]: {}", i, args[i]);
        }
    }
}

