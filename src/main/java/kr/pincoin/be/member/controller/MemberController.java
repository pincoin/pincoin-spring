package kr.pincoin.be.member.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@CrossOrigin("*")
@Slf4j
public class MemberController {
    @GetMapping("")
    public String Home() {
        return "Member controller";
    }
}
