package com.bookstore.Bookstore.controllers;

import java.util.ArrayList;
import java.util.GregorianCalendar;
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

import com.bookstore.Bookstore.domains.dto.BookRequest;
import com.bookstore.Bookstore.domains.models.AuthorEntity;
import com.bookstore.Bookstore.domains.models.BookEntity;
import com.bookstore.Bookstore.domains.models.GenreEntity;
import com.bookstore.Bookstore.domains.models.PictureEntity;
import com.bookstore.Bookstore.services.AuthorService;
import com.bookstore.Bookstore.services.BookService;
import com.bookstore.Bookstore.services.GenreService;
import com.bookstore.Bookstore.services.PictureService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    BookService bookService;

    @Autowired
    PictureService pictureService;

    @Autowired
    AuthorService authorService;

    @Autowired
    GenreService genreService;
    
    //Public path
    @GetMapping("/")
    public ResponseEntity<List<BookEntity>> getBooks(){
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }

    //Public path
    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable long id){
        Optional<BookEntity> bookOpt = bookService.getById(id);
        if(bookOpt.isPresent()){
            return ResponseEntity.ok().body(bookOpt.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    // return books by genre 
    //  @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    // public ArrayList<Book> getAuthorByName(@RequestBody GenreRequest request){
    //     Long author_id = jpaMainController.getAuthorIdByName(request.getName());
        
    //     if(author_id<0){
    //         return new ArrayList<Book>();
    //     }
        
    //     return jpaMainController.getBooksByAuthorId(author_id);
    // }

    //Create
    //Privated path
    @Transactional
    @PostMapping(value = "/auth/new", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createBook(@RequestBody BookRequest request){
        // if description is greater than 1024 don't save it into the database
        if(request.getDescription().length() > 1024){
            return ResponseEntity.badRequest().body("Description too long: "+request.getDescription().length());
        }
        
        try{
            
            // picture
            PictureEntity picture = pictureService.createPicture(new PictureEntity(request.getCover()));
            
            // Author
            AuthorEntity author = authorService.getById(request.getAuthor()).get();
            
            // genre
            List<GenreEntity> genres = new ArrayList<>();
            for(String genre : request.getGenres()){
                Optional<GenreEntity> genreGot = genreService.getById(Integer.parseInt(genre));
                if(genreGot.isPresent()){
                    genres.add(genreGot.get());
                }
            }
            
            // create Relase date 
            String[] date = request.getReleaseDate().split("-");
            GregorianCalendar releaseDate = new GregorianCalendar(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]));
            
            // book
            BookEntity book = new BookEntity();
            book.setName(request.getName());
            book.setDescription(request.getDescription());
            book.setAuthor(author);
            book.setCover(picture);
            book.setISBN(request.getIsbn());
            book.setPrice(request.getPrice());
            book.setQuantity(request.getQuantity());
            book.setPages(request.getPages());
            book.setPuntuation(request.getPuntuation());
            book.setGenres(genres);
            book.setReleaseDate(releaseDate);


            // Save book
            BookEntity createdBook = bookService.createBook(book);

            // update author with the created book
            List<BookEntity> authorBooks = author.getBooks();
            authorBooks.add(createdBook);
            author.setBooks(authorBooks);
            authorService.createAuthor(author);

        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>("Can't create the book.",HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.created(null).build();
    }

    // //Update
    // //Privated path
    // @PutMapping(value = "/book/auth/{id}", consumes = "application/json", produces = "application/json")
    // public ResponseEntity<String> editBook(@PathVariable long id, @RequestBody BookRequest request){
    //     if(request.getDescription().length() > 1024){

    //         return new ResponseEntity<>("Description too long: "+request.getDescription().length(), HttpStatus.BAD_REQUEST);
    //     }

    //     try{
    //         Book book = jpaMainController.getBook(id);
    //         book = BookService.checkDifferences(book,request,jpaMainController);
    //         jpaMainController.editBook(book);

    //     }catch (Exception e){
    //         System.out.println("Error update book: "+e);
    //         return new ResponseEntity<>("Error while update book.", HttpStatus.FORBIDDEN);
    //     }

    //     return new ResponseEntity<>(HttpStatus.OK);
    // }

    //Delete
    //Privated path
    @DeleteMapping(value = "/auth/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable long id){
        try {
            bookService.deleteBookById(id);
        }catch (Exception ex){
            return new ResponseEntity<String>("Can't delete the book since at least a book is pointing it.",HttpStatus.FORBIDDEN);
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
