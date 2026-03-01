package com.duytoan.imajicoffee.imaji_coffee_be.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Stripe config
 * @author duytoan
 * @since 10/2025
 */
@Configuration
@PropertySource("classpath:application.properties")
public class StripeConfig {
    @Value("${stripe.secretKey}")
    private String apiKey;

    @PostConstruct
    public void init(){
        Stripe.apiKey = apiKey;
    }
}
