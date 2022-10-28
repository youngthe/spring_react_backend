package com.example.backend.service;

import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value="select EXISTS (select * from user_tb where id=:id and pw=:pw)", nativeQuery = true)
    int findByIdAndPw(String id, String pw);

    @Query(value="select num, id, pw, role from user_tb where id=:id", nativeQuery = true)
    User findById(String id);
    @Transactional
    @Modifying
    @Query(value = "insert into user_tb(id, pw) values(:id, :pw)", nativeQuery = true)
    void setByUseridAndPw(String id, String pw);

}
