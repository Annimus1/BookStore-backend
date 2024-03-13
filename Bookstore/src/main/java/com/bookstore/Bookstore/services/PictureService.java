package com.bookstore.Bookstore.services;

import java.util.Optional;

import com.bookstore.Bookstore.domains.models.PictureEntity;
import com.bookstore.Bookstore.domains.repository.IPictureRepository;

public class PictureService {

    IPictureRepository pictureRepository;

    public PictureService(IPictureRepository pictureRepository){
        this.pictureRepository = pictureRepository;
    }

    // Create
    public PictureEntity createPicture(PictureEntity picture){
        return this.pictureRepository.save(picture);
    }

    // Delete
    public void deletePicture(PictureEntity picture){
        this.pictureRepository.delete(picture);
    }

    public void deletePictureById(long id){
        this.pictureRepository.deleteById(id);
    }

    // Get
    public Optional<PictureEntity> getById(long id){
        return this.pictureRepository.findById(id);
    }    
}
