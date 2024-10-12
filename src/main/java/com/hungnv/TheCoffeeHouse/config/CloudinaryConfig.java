package com.hungnv.TheCoffeeHouse.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@ConfigurationProperties(prefix = "cloudinary")
@RequiredArgsConstructor
public class CloudinaryConfig {

    @Value("${CLOUD_NAME}")
    private String cloudName;

    @Value("${CLOUD_API_KEY}")
    private String apiKey;

    @Value("${CLOUD_API_SECRET}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

}
