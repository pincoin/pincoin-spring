package kr.pincoin.be.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
@CrossOrigin("*")
@Slf4j
public class ShopController {
    @GetMapping("")
    public String Home() {
        return "Shop controller";
    }
}
