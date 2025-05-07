package com.zerobase.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${openweathermap.key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 현재 서울 날씨 데이터 가져오기
     */
    public WeatherResponse getWeather() {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=" + apiKey + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode root = objectMapper.readTree(response);

            String weather = root.get("weather").get(0).get("main").asText();
            String icon = root.get("weather").get(0).get("icon").asText();
            double temp = root.get("main").get("temp").asDouble();
            double humidity = root.get("main").get("humidity").asDouble();

            // 강수량 (rain 필드가 없을 수도 있으므로 안전하게 파싱)
            double precipitation = 0.0;
            if (root.has("rain")) {
                JsonNode rainNode = root.get("rain");
                if (rainNode.has("1h")) {
                    precipitation = rainNode.get("1h").asDouble();
                } else if (rainNode.has("3h")) {
                    precipitation = rainNode.get("3h").asDouble();
                }
            }

            return new WeatherResponse(weather, icon, temp, humidity, precipitation);
        } catch (IOException e) {
            throw new RuntimeException("날씨 데이터 파싱 실패", e);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class WeatherResponse {
        private String weather;
        private String icon;
        private double temperature;
        private double humidity;
        private double precipitation;
    }
}
