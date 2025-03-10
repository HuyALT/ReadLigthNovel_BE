package com.my_project.LightNovel_web_backend.controller.admin;

import com.my_project.LightNovel_web_backend.dto.request.GenreRequest;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.service.Genre.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/genre")
public class GenreAdminController {

    private final GenreService genreService;

    @PostMapping("/add")
    public ResponseEntity<?> addNewGenre(@RequestBody @Valid GenreRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.OK).body(genreService.addNew(request));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> deleteGenre(@RequestParam int id) {
        genreService.deleteGenre(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editGener(@RequestParam int id, @RequestBody GenreRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(genreService.editGenre(request, id));
    }
}
