package com.my_project.LightNovel_web_backend.controller.admin;

import com.my_project.LightNovel_web_backend.dto.request.LigthNovelRequest;
import com.my_project.LightNovel_web_backend.service.LightNovel.LigthNovelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.BindException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dashboard/")
public class LightNovelController {

    private final LigthNovelService ligthNovelService;

    @PostMapping
    public ResponseEntity<?> getAll(@RequestBody @Valid LigthNovelRequest request){

    }
}
