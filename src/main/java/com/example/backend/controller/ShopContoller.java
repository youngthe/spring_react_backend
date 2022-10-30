package com.example.backend.controller;

import com.example.backend.entity.Shop;
import com.example.backend.security.JwtTokenProvider;
import com.example.backend.service.BasketRepository;
import com.example.backend.service.ShopRepository;
import com.example.backend.service.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class ShopContoller {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    BasketRepository basketRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());


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

        if(!jwtTokenProvider.validateToken(token)){
            log.info("jwt_token error");
            result.put("resultCode", "jwt-expired");
            return result;
        }

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

    @RequestMapping(value="/addShop")
    public HashMap addShop(@RequestBody HashMap<String, String> data){

        HashMap<String,Object> result = new HashMap<>();

        String name = data.get("name");
        int price = Integer.parseInt(data.get("price"));
        log.info("name = {} , price = {}",name, price);

        try{
            shopRepository.setByNameAndPrice(name, price);
            result.put("resultCode", "true");

        }catch(Exception e){
            log.info("{}", e);
            result.put("resultCode", "false");
        }
        return result;
    }
}
