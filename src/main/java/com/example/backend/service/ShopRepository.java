package com.example.backend.service;

import com.example.backend.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    @Query(value="select * from shop", nativeQuery = true)
    List<Shop> getAllShop();


}
