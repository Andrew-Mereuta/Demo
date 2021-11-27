package com.example.moduleone;

import com.example.moduleone.models.Course;
import com.example.moduleone.reporsitories.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ModuleOne {

    public static void main(String[] args) {
        SpringApplication.run(ModuleOne.class, args);
    }

//    @Bean
//    public CommandLineRunner demo(RoleRepository repository) {
//        return (args) -> {
//            repository.save(new Role("STUDENT"));
//            repository.save(new Role("TA"));
//        };
//    }

    @Bean
    public CommandLineRunner demo(CourseRepository repository) {
        return (args) -> {
            repository.save(new Course("OOPP"));
            repository.save(new Course("OOP"));
        };
    }

}
