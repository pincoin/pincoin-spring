package kr.pincoin.be.django.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/django")
@CrossOrigin("*")
@Slf4j
public class DjangoController {
    @GetMapping("")
    public String Home() {
        return "Django controller";
    }
}
