package com.bookstore.Bookstore.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.Bookstore.domains.dto.AuthorRequest;
import com.bookstore.Bookstore.domains.models.AuthorEntity;
import com.bookstore.Bookstore.domains.models.BookEntity;
import com.bookstore.Bookstore.domains.models.PictureEntity;
import com.bookstore.Bookstore.services.AuthorService;
import com.bookstore.Bookstore.services.PictureService;

@RestController
@RequestMapping("/api/author")
@CrossOrigin("http://localhost:5173")
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @Autowired
    PictureService pictureService;

    // public path
    @GetMapping("/")
    public ResponseEntity<List<AuthorEntity>> getAuthors(){
        return ResponseEntity.ok().body(authorService.getAllAuthors());
    }
    
    // public path
    @GetMapping("/{id}")
    public ResponseEntity<AuthorEntity> getAuthor(@PathVariable long id){
        Optional<AuthorEntity> authorOpt = authorService.getById(id);
        if(authorOpt.isPresent()){
            return ResponseEntity.ok().body(authorOpt.get());
        }
        else{
            AuthorEntity author = new AuthorEntity();
            return ResponseEntity.ok().body(author);  
        }
    }
    
    // privated path
    @PostMapping(value = "/auth/new", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> newAuthor(@RequestBody AuthorRequest request){
        
        // if the lenght is greater than 1024 can't save it on the database
        if(request.getDescription().length() > 1024){
            return new ResponseEntity<>("Description too long: "+request.getDescription().length(), HttpStatus.BAD_REQUEST);
        }
        
        try{
            PictureEntity p = new PictureEntity(request.getImage_url());
            p = pictureService.createPicture(p);

            List<BookEntity> empList = new ArrayList<>();      
            AuthorEntity author = new AuthorEntity(request.getName(), request.getDescription(), p, empList);
            authorService.createAuthor(author);

             
        }catch(Exception err){
            System.out.println("Error Author already Exists: "+err);
            return new ResponseEntity<>("Author already on the System.",HttpStatus.FORBIDDEN);
        }
        
        return ResponseEntity.created(null).build();        
    }

    // // Privated path
    @DeleteMapping("/auth/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable long id){
        try {
            authorService.deleteAuthorById(id);
        }catch (Exception ex){
            return new ResponseEntity<String>("Can't delete the author since at least a book is pointing it.",HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok().build();
    }

}
