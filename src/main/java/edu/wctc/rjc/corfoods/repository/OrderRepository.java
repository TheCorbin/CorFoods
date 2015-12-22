package edu.wctc.rjc.corfoods.repository;

import edu.wctc.rjc.corfoods.entity.Order;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author rCorbin
 */
public interface OrderRepository extends JpaRepository<Order, Integer>, Serializable {
    
    @Query("SELECT a FROM Order a JOIN FETCH a.orderItems WHERE a.orderId = (:id)")
    public Order findByIdAndFetchOrderItemsEagerly(@Param("id") Integer id);
    
    
    @Query("SELECT DISTINCT a FROM Order a JOIN FETCH a.orderItems ")
    public List<Order> findAllAndFetchOrderItemsEagerly();
    
}
