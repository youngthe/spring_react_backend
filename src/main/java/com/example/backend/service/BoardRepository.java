package com.example.backend.service;

import com.example.backend.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "select id, title from board_table", nativeQuery = true)
    public List<Board> get_Board();

    @Transactional
    @Modifying
    @Query(value = "insert into board_table(title) values (:title)", nativeQuery = true)
    public void set_Board(String title);

}
