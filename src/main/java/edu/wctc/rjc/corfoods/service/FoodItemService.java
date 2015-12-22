package edu.wctc.rjc.corfoods.service;

import edu.wctc.rjc.corfoods.entity.FoodItem;
import edu.wctc.rjc.corfoods.repository.FoodItemRepository;
import edu.wctc.rjc.corfoods.repository.UserRepository;
import edu.wctc.rjc.corfoods.repository.OrderRepository;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a special Spring-enabled service class that delegates work to 
 * various Spring managed repository objects using special spring annotations
 * to perform dependency injection, and special annotations for transactions.
 * It also uses SLF4j to provide logging features.
 * 
 * @author jlombardo
 */
@Repository("foodItemService")
@Transactional(readOnly = true)
public class FoodItemService {

    private transient final Logger LOG = LoggerFactory.getLogger(FoodItemService.class);

    @Autowired
    private FoodItemRepository foodItemRepo;

    
    public FoodItemService() {
    }

    public List<FoodItem> findAll() {
        return foodItemRepo.findAll();
    }
    
//    public FoodItem findByIdAndFetchBooksEagerly(String id) {
//        Integer authorId = new Integer(id);
//        return foodItemRepo.findByIdAndFetchBooksEagerly(authorId);
//    }

    public FoodItem findById(String id) {
        return foodItemRepo.findOne(new Integer(id));
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void remove(FoodItem foodItem) {
        LOG.debug("Deleting foodItem: " + foodItem.getFoodName());
        foodItemRepo.delete(foodItem);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public FoodItem edit(FoodItem foodItem) {
        return foodItemRepo.saveAndFlush(foodItem);
    }
    
    public void deleteById(Integer id) {
        foodItemRepo.deleteById(id);
    }
    
}
