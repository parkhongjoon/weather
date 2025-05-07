package com.zerobase.weather.scheduler;

import com.zerobase.weather.domain.Diary;
import com.zerobase.weather.repository.DiaryRepository;
import com.zerobase.weather.service.WeatherService;
import com.zerobase.weather.service.WeatherService.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherScheduler {

    private final WeatherService weatherService;
    private final DiaryRepository diaryRepository;

    /**
     * 매일 새벽 1시에 날씨 저장
     */
    @Scheduled(cron = "0 0 1 * * *") // 매일 01:00:00
    public void saveWeatherDaily() {
        log.info("날씨 저장 스케줄 시작");

        WeatherResponse weather = weatherService.getWeather();

        Diary diary = Diary.builder()
                .weather(weather.getWeather())
                .icon(weather.getIcon())
                .temperature(weather.getTemperature())
                .humidity(weather.getHumidity())
                .precipitation(weather.getPrecipitation())
                .text("날씨 데이터 저장 (자동)")
                .date(LocalDate.now())
                .build();

        diaryRepository.save(diary);

        log.info("날씨 저장 완료: {}", diary);
    }
}
