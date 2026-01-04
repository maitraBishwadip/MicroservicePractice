package Microservices.OrderService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelmapper() {
        return new ModelMapper();
    }



}

