package edu.wctc.rjc.corfoods.service;

import edu.wctc.rjc.corfoods.entity.OrderItem;
import edu.wctc.rjc.corfoods.repository.OrderItemRepository;
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
@Repository("orderItemService")
@Transactional(readOnly = true)
public class OrderItemService {

    private transient final Logger LOG = LoggerFactory.getLogger(OrderItemService.class);

    @Autowired
    private OrderItemRepository orderItemRepo;

    
    public OrderItemService() {
    }

    public List<OrderItem> findAll() {
        return orderItemRepo.findAll();
    }
    
//    public FoodItem findByIdAndFetchBooksEagerly(String id) {
//        Integer authorId = new Integer(id);
//        return foodItemRepo.findByIdAndFetchBooksEagerly(authorId);
//    }

    public OrderItem findById(String id) {
        return orderItemRepo.findOne(new Integer(id));
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void remove(OrderItem foodItem) {
        LOG.debug("Deleting author: " + foodItem.getFoodName());
        orderItemRepo.delete(foodItem);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public OrderItem edit(OrderItem orderItem) {
        return orderItemRepo.saveAndFlush(orderItem);
    }
}
