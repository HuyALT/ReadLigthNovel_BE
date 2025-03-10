package com.my_project.LightNovel_web_backend.controller.admin;

import com.my_project.LightNovel_web_backend.dto.request.LigthNovelRequest;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.service.LightNovel.LigthNovelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/LightNovel")
public class LightNovelAdminController {

    private final LigthNovelService ligthNovelService;

    @PostMapping("/add")
    public ResponseEntity<?> addNewLigthNovel(@RequestBody @Valid LigthNovelRequest request,
                                              BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(ligthNovelService.addNew(request));
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editLightNovel(@RequestParam long id,
                                            @RequestBody @Valid LigthNovelRequest request,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(ligthNovelService.editLigthNovel(id, request));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> deleteLightNovel(@RequestParam long id) {
        if (ligthNovelService.deleteLigthNovel(id)) {
            return ResponseEntity.ok("Delete Success");
        }
        return ResponseEntity.internalServerError().body("Server error");
    }
}
