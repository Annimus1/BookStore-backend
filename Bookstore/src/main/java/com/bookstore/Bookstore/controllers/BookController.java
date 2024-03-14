package com.bookstore.Bookstore.controllers;

import java.util.ArrayList;
import java.util.GregorianCalendar;
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
@CrossOrigin("http://localhost:5173")
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
    
    /**
     * Bring the books by the name of the author
     * @param @link AuthorRequest were we only care about the name.
     * @return List<BookEntity>
    */
    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<BookEntity>> getAuthorByName(@RequestBody AuthorRequest request){
        System.out.println(request.getName());
        try{
            AuthorEntity author = authorService.getByName(request.getName());
            if(author!=null){

                long author_id = author.getId();
                
                if(author_id<0 || author == null){
                    return ResponseEntity.notFound().build();
                }
                // Todo:
                // return books where author_id = author id
                return ResponseEntity.ok().body(bookService.getBooksByAuthor(author_id));
            }else{
                throw new Exception("the author is null");
            }
        }catch (Exception err){
            return ResponseEntity.notFound().build();
        }
    }

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
