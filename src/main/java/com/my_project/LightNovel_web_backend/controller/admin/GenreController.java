package com.my_project.LightNovel_web_backend.controller.admin;

import com.my_project.LightNovel_web_backend.dto.request.GenreRequest;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.service.Genre.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dashboard")
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/addGenre")
    public ResponseEntity<?> addNewGenre(@RequestBody @Valid GenreRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.OK).body(genreService.addNew(request));
    }
}
