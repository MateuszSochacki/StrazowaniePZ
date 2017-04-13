package dao;

import model.CategoryEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by msoch_000 on 13-04-2017.
 */

@Transactional
@Repository
public interface CategoryDao extends org.springframework.data.repository.PagingAndSortingRepository<CategoryEntity, Integer> {



}
