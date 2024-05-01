package com.stackroute.paymentservice.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Data
@Component
@Configuration
public class RazorPayClientConfig {
    private String key;
    private String secret;

}
