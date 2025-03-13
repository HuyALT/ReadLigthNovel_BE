package com.my_project.LightNovel_web_backend.service.Genre;

import com.my_project.LightNovel_web_backend.dto.request.GenreRequest;
import com.my_project.LightNovel_web_backend.dto.response.GenreReponse;

import java.util.List;

public interface IGenreService {
    GenreReponse addNew(GenreRequest request);
    void deleteGenre(int id);
    List<GenreReponse> getAllGenre();
    GenreReponse updateGenre(GenreRequest request, int id);
    GenreReponse getById(int id);

}
