package edu.wctc.rjc.corfoods.repository;

import edu.wctc.rjc.corfoods.entity.OrderItem;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jlombardo
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>, Serializable {
    
}
