package edu.wctc.rjc.corfoods.repository;

import edu.wctc.rjc.corfoods.entity.User;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 *
 * @author jlombardo
 */
public interface UserRepository extends JpaRepository<User, Integer>, Serializable {
    
}
