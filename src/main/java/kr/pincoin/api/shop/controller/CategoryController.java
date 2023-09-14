package kr.pincoin.api.shop.controller;

import kr.pincoin.api.shop.dto.CategoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
@Slf4j
public class CategoryController {
    @GetMapping("")
    public ResponseEntity<List<CategoryResponse>>
    companyList() {

        return null;
    }
}
