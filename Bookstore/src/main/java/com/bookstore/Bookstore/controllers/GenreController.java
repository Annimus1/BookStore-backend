package com.bookstore.Bookstore.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.Bookstore.domains.models.GenreEntity;
import com.bookstore.Bookstore.services.GenreService;

@RestController
@RequestMapping("api/genre")
public class GenreController {
    @Autowired
    GenreService genreService;
    //Public path
    @GetMapping("/")
    public ResponseEntity<List<GenreEntity>> getGenres(){
        List<GenreEntity> genres =  genreService.getAllGenres();
        return ResponseEntity.ok().body(genres);
    }

    //Public path
    @GetMapping("/{id}")
    public ResponseEntity<String> getGenre(@PathVariable long id){
        Optional<GenreEntity> genre =  genreService.getById(id);
        if(genre.isPresent()){
            GenreEntity response = new GenreEntity();
            response.setId(genre.get().getId());
            response.setName(genre.get().getName());
            return ResponseEntity.ok().body(response.toString());
        }
        else{
            return new ResponseEntity<>("Genre not found.",HttpStatus.NOT_FOUND);
        }
    }

    //Privated path
    @PostMapping(value = "/auth/new")
    public ResponseEntity<String> createGenre(@RequestBody GenreEntity request){
        try{
            // Create new genre
            GenreEntity genre = new GenreEntity(request.getName());
            genreService.createGenre(genre);
            
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(Exception e ){
            return new ResponseEntity<>("Genre already in use.",HttpStatus.FORBIDDEN);
        }

    }

    //Privated path
    @DeleteMapping("/auth/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable long id){
        try{
            genreService.deleteGenreById(id);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>("Unable to delete the genre since it doesn't longer exist.", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
