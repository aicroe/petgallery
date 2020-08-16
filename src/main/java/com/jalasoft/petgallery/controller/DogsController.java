package com.jalasoft.petgallery.controller;

import com.jalasoft.petgallery.dogs.Dog;
import com.jalasoft.petgallery.dogs.DogService;
import com.jalasoft.petgallery.termscore.Term;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("dogs")
public class DogsController {

    private DogService dogService;

    public DogsController(DogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping("all")
    public List<Dog> getAllDogs() {
        return dogService.findAll();
    }

    @GetMapping
    public List<Dog> getDogs(@RequestParam(defaultValue = "") Term filter) {
        System.out.println(filter);
        return dogService.findBy(filter);
    }
}
