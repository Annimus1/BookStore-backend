package com.bookstore.Bookstore.controllers;

import com.bookstore.Bookstore.domains.dto.UserRequest;
import com.bookstore.Bookstore.domains.models.UserEntity;
import com.bookstore.Bookstore.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    
    // Public Endpoint
    @PostMapping(value = "/auth/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> Register(@RequestBody UserRequest request){
        System.out.println(request.getEmail());
        
        try{  
            // create the user

            UserEntity user = new UserEntity(request);
            UserEntity userCreated = userService.createUser(user);
            System.out.println("User creared.");
            
            ResponseEntity<String> resp = ResponseEntity.ok().body(userCreated.toString());
            return resp;
            
        }catch(Exception e){
            System.out.println("Error on Register: "+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    // public path
    // @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    // public ResponseEntity<String> Login(@RequestBody UserApp request){
        
    //     JpaMainController jpaMainController = new JpaMainController();
        
    //     try{  
    //         // get the user
    //         UserApp user = jpaMainController.getUser(request.getEmail());
          
    //         // check if email and password match
    //         if( user != null && LoginServices.match(request.getPassword(), user.getPassword())){
                
    //             // at this point should send the headers in order to
    //             // allow see the protected paths.
    //             // ---------------------------------------------------------------------------
    //             return new ResponseEntity<>("Passwd & email matches.",HttpStatus.OK);
    //         }
            
    //         return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            
    //     }catch(Exception e){
    //         System.out.println("Error on Register: "+e);
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
}
