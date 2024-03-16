package com.bookstore.Bookstore.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookstore.Bookstore.domains.models.UserEntity;
import com.bookstore.Bookstore.domains.repository.IUserRepository;

@Service
public class UserService {
    public IUserRepository userRepository;

    public UserService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity user){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    public UserEntity updateUser(UserEntity user){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwd = passwordEncoder.encode(user.getPassword());
        UserEntity oldUser = this.userRepository.findById(user.getId()).get();
        if(passwd.equals(oldUser.getPassword())){
            return this.userRepository.save(user);
        }else{
            return this.createUser(user);
        }
    }

    public void deleteUser(UserEntity user){
        this.userRepository.delete(user);
    }

    public void deleteUserById(long id){
        this.userRepository.deleteById(id);
    }

    public Optional<UserEntity> getById(long id){
        return this.userRepository.findById(id);
    }

    public UserEntity getByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public List<UserEntity> getAllUsers(){
        return this.userRepository.findAll();
    }

}
