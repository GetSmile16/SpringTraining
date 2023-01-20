package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoApplicationConfig {
    @Bean
    public String imagePath()
    {
        return "C:\\Users\\zar16\\IdeaProjects\\SpringTraining\\src\\main\\resources\\static\\imgs\\";
    }
}
