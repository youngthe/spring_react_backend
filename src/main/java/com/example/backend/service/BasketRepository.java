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
    void setByUseridAndItemid(String userid, Integer item_id);


    @Query(value="select EXISTS (select userid, item_id from basket where userid=:userid and item_id=:item_id)", nativeQuery = true)
    int existsByUseridAndItemid(String userid, int item_id);

    @Transactional
    @Modifying
    @Query(value="delete from basket where userid=:userid and item_id =:item_id", nativeQuery = true)
    void deleteByUseridAndItemid(String userid, Integer item_id);

}
