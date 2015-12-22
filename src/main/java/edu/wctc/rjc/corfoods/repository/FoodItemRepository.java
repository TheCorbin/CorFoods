package edu.wctc.rjc.corfoods.repository;

import edu.wctc.rjc.corfoods.entity.FoodItem;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author jlombardo
 */
public interface FoodItemRepository extends JpaRepository<FoodItem, Integer>, Serializable {

    @Query("delete from FoodItem f where f.foodId = :id")
    void deleteById(@Param("id") Integer id);
    
}
