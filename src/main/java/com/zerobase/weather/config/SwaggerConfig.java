package com.zerobase.weather.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI weatherDiaryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Weather Diary API")
                        .description("날씨 일기 프로젝트 API 명세서입니다.")
                        .version("v1.0"));
    }
}
