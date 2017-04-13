package controller;

import dao.CategoryDao;
import model.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

/**
 * Created by msoch_000 on 03-04-2017.
 */


@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private CategoryDao categoryDao;


    public void test() {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("Fantasy");
        categoryDao.save(categoryEntity);

    }
}
