package ru.nsk.java.tasktest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;


@SpringBootApplication
public class TaskTestApplication implements CommandLineRunner {

    private static Logger LOG = LoggerFactory
            .getLogger(TaskTestApplication.class);
    public static void main(String[] args) throws IOException {
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

