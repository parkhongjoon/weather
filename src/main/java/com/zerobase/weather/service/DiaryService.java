package com.zerobase.weather.service;

import com.zerobase.weather.domain.Diary;
import com.zerobase.weather.repository.DiaryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final WeatherService weatherService;  //  날씨 서비스 주입

    /**
     * 일기 저장
     */
    @Transactional
    public void createDiary(LocalDate date, String text) {
        //  실제 날씨 데이터 받아오기
        WeatherService.WeatherResponse weather = weatherService.getWeather();

        Diary diary = Diary.builder()
                .weather(weather.getWeather())
                .icon(weather.getIcon())
                .temperature(weather.getTemperature())
                .humidity(weather.getHumidity())
                .precipitation(weather.getPrecipitation())
                .text(text)
                .date(date)
                .build();

        diaryRepository.save(diary);

        // 로그 추가
        log.info("일기 저장: 날짜={}, 날씨={}, 온도={}, 습도={}, 강수량={}, 내용={}",
                date, weather.getWeather(), weather.getTemperature(),
                weather.getHumidity(), weather.getPrecipitation(), text);
    }

    /**
     * 특정 날짜 일기 조회
     */
    public List<Diary> readDiary(LocalDate date) {
        List<Diary> diaries = diaryRepository.findAllByDate(date);
        diaries.forEach(diary ->
                log.info("조회된 일기: 날짜={}, 날씨={}, 온도={}, 습도={}, 강수량={}, 내용={}",
                        diary.getDate(), diary.getWeather(), diary.getTemperature(),
                        diary.getHumidity(), diary.getPrecipitation(), diary.getText())
        );
        return diaries;
    }

    /**
     * 기간별 일기 조회
     */
    public List<Diary> readDiaries(LocalDate startDate, LocalDate endDate) {
        List<Diary> diaries = diaryRepository.findAllByDateBetween(startDate, endDate);
        diaries.forEach(diary ->
                log.info("조회된 기간 일기: 날짜={}, 날씨={}, 온도={}, 습도={}, 강수량={}, 내용={}",
                        diary.getDate(), diary.getWeather(), diary.getTemperature(),
                        diary.getHumidity(), diary.getPrecipitation(), diary.getText())
        );
        return diaries;
    }

    /**
     * 첫 번째 일기 수정
     */
    @Transactional
    public void updateDiary(LocalDate date, String text) {
        List<Diary> diaries = diaryRepository.findAllByDate(date);
        if (!diaries.isEmpty()) {
            Diary firstDiary = diaries.get(0);
            String oldText = firstDiary.getText();
            firstDiary.setText(text);
            diaryRepository.save(firstDiary);
            log.info("일기 수정: 날짜={}, 이전 내용={}, 새로운 내용={}", date, oldText, text);
        }
    }

    /**
     * 특정 날짜의 모든 일기 삭제
     */
    @Transactional
    public void deleteDiary(LocalDate date) {
        diaryRepository.deleteAllByDate(date);
        log.info("일기 삭제: 날짜={}", date);
    }
}