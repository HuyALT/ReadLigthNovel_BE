package com.my_project.LightNovel_web_backend.controller;

import com.my_project.LightNovel_web_backend.service.Genre.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/genre")
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<?> getGenre(@RequestParam(required = false) Integer id) {
        if (id==null) {
            return ResponseEntity.ok(genreService.getAllGenre());
        }
        return ResponseEntity.ok(genreService.getById(id));
    }
}
