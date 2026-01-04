package Microservices.OrderService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import org.modelmapper.ModelMapper;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelmapper() {
        return new ModelMapper();
    }

}

