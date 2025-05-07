package com.zerobase.weather.controller;

import com.zerobase.weather.domain.Diary;
import com.zerobase.weather.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    /**
     * 일기 생성
     */
    @Operation(summary = "일기 생성", description = "특정 날짜에 날씨와 텍스트를 포함한 일기를 생성합니다.")
    @PostMapping("/create")
    public void createDiary(
            @Parameter(description = "일기를 작성할 날짜 (yyyy-MM-dd)", example = "2025-05-04")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody String text
    ) {
        diaryService.createDiary(date, text);
    }

    /**
     * 특정 날짜의 일기 조회
     */
    @Operation(summary = "일기 조회", description = "특정 날짜의 일기를 조회합니다.")
    @GetMapping("/read")
    public List<Diary> readDiary(
            @Parameter(description = "조회할 날짜 (yyyy-MM-dd)", example = "2025-05-04")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return diaryService.readDiary(date);
    }

    /**
     * 특정 기간의 일기 전체 조회
     */
    @Operation(summary = "기간 일기 조회", description = "지정된 기간 동안의 모든 일기를 조회합니다.")
    @GetMapping("/read/all")
    public List<Diary> readDiaries(
            @Parameter(description = "시작 날짜 (yyyy-MM-dd)", example = "2025-05-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "종료 날짜 (yyyy-MM-dd)", example = "2025-05-04")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return diaryService.readDiaries(startDate, endDate);
    }

    /**
     * 일기 수정
     */
    @Operation(summary = "일기 수정", description = "특정 날짜의 첫 번째 일기를 수정합니다.")
    @PutMapping("/update")
    public void updateDiary(
            @Parameter(description = "수정할 날짜 (yyyy-MM-dd)", example = "2025-05-04")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody String text
    ) {
        diaryService.updateDiary(date, text);
    }

    /**
     * 일기 삭제
     */
    @Operation(summary = "일기 삭제", description = "특정 날짜의 모든 일기를 삭제합니다.")
    @DeleteMapping("/delete")
    public void deleteDiary(
            @Parameter(description = "삭제할 날짜 (yyyy-MM-dd)", example = "2025-05-04")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        diaryService.deleteDiary(date);
    }
}
