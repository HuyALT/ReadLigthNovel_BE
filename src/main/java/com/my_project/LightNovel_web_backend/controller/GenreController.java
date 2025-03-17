package com.my_project.LightNovel_web_backend.controller;

import com.my_project.LightNovel_web_backend.service.Genre.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/genre")
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getGenre(@PathVariable Integer id) {
        return ResponseEntity.ok(genreService.findById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllGenre() {
        return ResponseEntity.ok(genreService.findAllGenre());
    }
}
