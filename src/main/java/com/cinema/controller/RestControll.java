package com.cinema.controller;

import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dominik on 14.04.2017.
 */
@RestController("/movie")
public class RestControll {

    /*private final CategoryRepository categoryDao;

    @Autowired
    public RestControll(CategoryRepository categoryDao) {
        this.categoryDao = categoryDao;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addCategory(CategoryEntity categoryEntity){
        categoryDao.save(categoryEntity);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<CategoryEntity> getCategory(){
        return categoryDao.findAll();
    }*/
}
