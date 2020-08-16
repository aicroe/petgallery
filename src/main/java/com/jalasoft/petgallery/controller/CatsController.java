package com.jalasoft.petgallery.controller;

import com.jalasoft.petgallery.cats.Cat;
import com.jalasoft.petgallery.cats.CatService;
import com.jalasoft.petgallery.termscore.Term;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cats")
public class CatsController {

    private CatService catService;

    public CatsController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("all")
    public List<Cat> getAllCats() {
        return catService.findAll();
    }

    @GetMapping
    public List<Cat> getCats(@RequestParam(defaultValue = "") Term filter) {
        System.out.println(filter);
        return catService.findBy(filter);
    }
}
