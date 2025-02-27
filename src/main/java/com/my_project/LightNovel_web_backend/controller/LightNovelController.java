package com.my_project.LightNovel_web_backend.controller;

import com.my_project.LightNovel_web_backend.service.LightNovel.LigthNovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/lightnovel")
public class LightNovelController {

    private final LigthNovelService ligthNovelService;

    @GetMapping
    public ResponseEntity<?> getAllByChapterUpdate(@PageableDefault(size = 100)Pageable pageable){
        Pageable pageableRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageNumber()
        );
        return ResponseEntity.status(HttpStatus.OK).body(ligthNovelService.findByLatestChapterUpdate(pageableRequest));
    }

    @GetMapping("/all")
    public  ResponseEntity<?> getAll(@PageableDefault(size = 100)Pageable pageable){
        Pageable pageableRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageNumber()
        );
        return ResponseEntity.status(HttpStatus.OK).body(ligthNovelService.findAll(pageableRequest));
    }

    @GetMapping("/genresAll")
    public ResponseEntity<?> getByGenre(@PageableDefault(size = 100)Pageable pageable,
                                        @RequestBody List<String> genres){
        Pageable pageableRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageNumber()
        );
        return ResponseEntity.status(HttpStatus.OK).body(ligthNovelService.findByGeners(genres, pageable));
    }

    @GetMapping("/genreFillter")
    public ResponseEntity<?> getByGenreLastestChapterUpdate(@PageableDefault(size = 100)Pageable pageable,
                                                            @RequestBody List<String> genres){
        Pageable pageableRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageNumber()
        );
        return ResponseEntity.status(HttpStatus.OK).body(ligthNovelService.findByGenreSortByLastestChapterUpdate(genres, pageable));
    }
}
