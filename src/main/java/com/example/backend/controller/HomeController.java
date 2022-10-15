package com.example.backend.controller;

import com.example.backend.entity.Board;
import com.example.backend.entity.User;
import com.example.backend.security.JwtTokenProvider;
import com.example.backend.service.BoardRepository;
import com.example.backend.service.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @ResponseBody
    @RequestMapping("/api/board")
    public List<Board> getAllBoards() {
        System.out.println("/api/board");
        return boardRepository.get_Board();
    }



    @RequestMapping(value = "/api/board", method = RequestMethod.POST)
    public void createBoard(@RequestBody Board board) {
        log.info("/api/board");

        boardRepository.set_Board(board.getTitle());
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HashMap<String, String> login(@RequestBody HashMap<String, String> data) {
        log.info("/login");
        log.info("id : {}", data.get("id"));
        log.info("pw : {}", data.get("pw"));

        HashMap<String, String> result = new HashMap<>();
        String id = data.get("id");
        String pw = data.get("pw");

        if(userRepository.searchUser(id, pw) == 0) {

            log.info("not found user");
            result.put("resultCode", "false");
            return result;
        }else{
            log.info("success login : {}", id);
            result.put("resultCode", "true");
            User user = userRepository.getUser(id);
            String token = jwtTokenProvider.createToken(user);
            result.put("jwt_token", token);
            return result;
        }
    }

    @RequestMapping(value = "/search")
    public HashMap search(@RequestBody HashMap<String, String> data){
        HashMap<String, String> result = new HashMap<>();

        if(jwtTokenProvider.validateToken(data.get("jwt_token"))){
            String role = jwtTokenProvider.getUserRole(data.get("jwt_token"));
            log.info("role : {}", role);
            result.put("resultCode", "true");
        }else{
            log.info("jwt_token error");
            result.put("resultCode", "jwt-error");
        }
        return result;
    }


    @RequestMapping(value = "/register")
    public HashMap register(@RequestBody HashMap<String, String> data){
        HashMap<String, String> result = new HashMap<>();

        String id = data.get("id");
        String pw = data.get("pw");

        log.info("id : {}", id);
        log.info("pw : {}", pw);

        try{
            userRepository.registerUser(id, pw);
            result.put("resultCode", "true");
        }catch(Exception e){
            result.put("resultCode", "false");
        }

        return result;
    }


}
