package com.example.backend.service;

import com.example.backend.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Integer> {

    @Transactional
    @Modifying
    @Query(value="insert into basket(userid, item_id) values (:userid, :item_id)", nativeQuery = true)
    void setBasket(String userid, Integer item_id);

}
