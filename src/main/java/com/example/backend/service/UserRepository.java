package com.example.backend.service;

import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value="select EXISTS (select * from user_tb where id=:id and pw=:pw)", nativeQuery = true)
    int searchUser(String id, String pw);


}
