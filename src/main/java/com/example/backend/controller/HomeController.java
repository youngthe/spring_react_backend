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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/search")
    public HashMap search(@RequestBody HashMap<String, String> data){
        HashMap<String, Object> result = new HashMap<>();

        if(jwtTokenProvider.validateToken(data.get("jwt_token"))){
            String role = jwtTokenProvider.getUserRole(data.get("jwt_token"));
            log.info("role : {}", role);
            result.put("resultCode", "true");
        }else{
            log.info("jwt_token error");
            result.put("resultCode", "jwt-expired");
        }
        return result;
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

    @RequestMapping(value = "/shopping")
    public HashMap main_shop(){
        HashMap<String, Object> result = new HashMap<>();

        List<Shop> list;
        list = shopRepository.findAllShop();

        result.put("list", list);
        result.put("resultCode", "true");
        return result;
    }


    @RequestMapping(value = "/my-shop/{id}")
    public HashMap shop_back(@PathVariable("id") Integer num, @RequestBody HashMap<String, String> data){
        HashMap<String, Object> result = new HashMap<>();

        String token = data.get("jwt_token");
        log.info(token);


        if(jwtTokenProvider.validateToken(token)){

            String userid = jwtTokenProvider.getUserId(token);
            try{
                if(basketRepository.existsByUseridAndItemid(userid, num) == 1){
                    log.info("장바구니에 이미 존재합니다.");
                    result.put("resultCode", "true");
                    result.put("message", "exist");
                }else{
                    basketRepository.setByUseridAndItemid(userid, num);
                    result.put("resultCode", "true");
                }

            }catch(Exception e){
                log.info("my-shop error");
                log.info("{}", e);
                result.put("resultCode", "false");
            }
        }else{
            log.info("jwt-expired");
            result.put("resultCode", "jwt-expired");
        }


        return result;
    }

    @RequestMapping(value = "/show-my-shopping")
    public HashMap showMyShopping(@RequestBody HashMap<String, String> data){
        HashMap<String, Object> result = new HashMap<>();
        String token = data.get("jwt_token");
        String userid = jwtTokenProvider.getUserId(token);
        List<Shop> list = shopRepository.findByUserid(userid);
        result.put("list", list);
        result.put("resultCode", "true");
        return result;
    }

    @RequestMapping(value = "/delete-my-shopping/{id}")
    public HashMap deleteMyShopping(@PathVariable("id") Integer num, @RequestBody HashMap<String, String> data){
        HashMap<String, Object> result = new HashMap<>();
        String token = data.get("jwt_token");
        String id = jwtTokenProvider.getUserId(token);
        try{
            basketRepository.deleteByUseridAndItemid(id, num);
            result.put("resultCode", "true");
            return result;
        }catch(Exception e){
            log.info("{}", e);
            result.put("resultCode","false");
            return result;
        }
    }
}
