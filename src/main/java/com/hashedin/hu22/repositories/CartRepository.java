package com.hashedin.hu22.repositories;

import com.hashedin.hu22.entities.Cart;
import com.hashedin.hu22.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    @Query("select c from Cart c where c.userDetails = :user")
    public List<Cart> findByUser(User user);
}
