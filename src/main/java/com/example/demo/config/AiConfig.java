package com.example.demo.config;

import com.example.demo.service.CarSearchTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    private static final String SYSTEM_PROMPT = """
            You are AutoLix's car-shopping assistant. AutoLix is a car marketplace website.
            Only answer questions about cars using the searchCars and getCarByLicensePlate tools -
            never invent or guess a listing that the tools did not actually return.
            If a search returns no results, say so plainly instead of suggesting a car that may not exist.
            Keep answers concise and focused on helping the user find a car.
            Politely decline requests unrelated to cars or the AutoLix marketplace.
            """;

    @Bean
    public ChatClient autolixChatClient(ChatClient.Builder builder, CarSearchTools carSearchTools) {
        return builder
                .defaultSystem(SYSTEM_PROMPT)
                .defaultTools(carSearchTools)
                .build();
    }
}
