package com.example.backend.controller;

import com.example.backend.entity.Board;
import com.example.backend.service.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HomeController {
    @Autowired
    BoardRepository boardRepository;

    @ResponseBody
    @RequestMapping("/api/board")
    public List<Board> getAllBoards() {
        System.out.println("/api/board");
        return boardRepository.get_Board();
    }



    @RequestMapping(value = "/api/board", method = RequestMethod.POST)
    public void createBoard(@RequestBody Board board) {
        System.out.println("/api/board");
        System.out.println("post");
        boardRepository.set_Board(board.getTitle());
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestBody HashMap<String, String> data) {
        System.out.println("/login");
        System.out.println(data.get("id"));
        System.out.println(data.get("pw"));
    }
}
