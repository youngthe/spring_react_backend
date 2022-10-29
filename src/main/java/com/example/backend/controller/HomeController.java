package com.example.backend.controller;

import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import com.example.backend.security.JwtTokenProvider;
import com.example.backend.service.BasketRepository;
import com.example.backend.service.ShopRepository;
import com.example.backend.service.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class HomeController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    BasketRepository basketRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());



    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HashMap login(@RequestBody HashMap<String, String> data) {
        log.info("/login");
        log.info("id : {}", data.get("id"));
        log.info("pw : {}", data.get("pw"));

        HashMap<String, Object> result = new HashMap<>();
        String id = data.get("id");
        String pw = data.get("pw");

        if(userRepository.findByIdAndPw(id, pw) == 0) {

            log.info("not found user");
            result.put("resultCode", "false");
            return result;
        }else{
            log.info("success login : {}", id);
            result.put("resultCode", "true");
            User user = userRepository.findById(id);
            String token = jwtTokenProvider.createToken(user);
            result.put("jwt_token", token);
            return result;
        }
    }

    @RequestMapping(value = "/register")
    public HashMap register(@RequestBody HashMap<String, String> data){
        HashMap<String, Object> result = new HashMap<>();

        String id = data.get("id");
        String pw = data.get("pw");

        log.info("id : {}", id);
        log.info("pw : {}", pw);

        try{
            if(userRepository.existById(id) == 0){
                userRepository.setByUseridAndPw(id, pw);
                result.put("resultCode", "true");
            }else{
                result.put("resultCode", "false");
                result.put("message", "exist");
            }

        }catch(Exception e){
            result.put("resultCode", "false");
        }
        return result;
    }


}
