package edu.wctc.rjc.corfoods.service;

import edu.wctc.rjc.corfoods.entity.*;
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
@Repository("orderService")
@Transactional(readOnly = true)
public class OrderService {

    private transient final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    @Inject
    private FoodItemRepository foodItemRepo;

    @Inject
    private OrderRepository orderRepo;

    @Inject
    private UserRepository userRepo;

    public OrderService() {
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }
    
    public List<Order> findAllAndFetchOrderItemsEagerly() {
        return orderRepo.findAllAndFetchOrderItemsEagerly();
    }

    public Order findById(String id) {
        return orderRepo.findOne(new Integer(id));
    }
    
    public Order findByIdAndFetchOrderItemsEagerly(String id){
        Integer orderId = new Integer(id);
        return orderRepo.findByIdAndFetchOrderItemsEagerly(orderId);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void remove(Order order) {
        LOG.debug("Deleting order: " + order.getCustomerEmail());
        orderRepo.delete(order);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Order edit(Order order) {
        return orderRepo.saveAndFlush(order);
    }
}
