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
@Repository("userService")
@Transactional(readOnly = true)
public class UserService {

    private transient final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Inject
    private FoodItemRepository foodItemRepo;

    @Inject
    private OrderRepository orderRepo;

    @Inject
    private UserRepository userRepo;

    public UserService() {
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }
    
//    public User findByIdAndFetchBooksEagerly(String id) {
//        Integer authorId = new Integer(id);
//        return userRepo.findByIdAndFetchBooksEagerly(authorId);
//    }

    public User findById(String id) {
        return userRepo.findOne(new Integer(id));
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void remove(User user) {
        LOG.debug("Deleting author: " + user.getUsername());
        userRepo.delete(user);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User edit(User user) {
        return userRepo.saveAndFlush(user);
    }
}
