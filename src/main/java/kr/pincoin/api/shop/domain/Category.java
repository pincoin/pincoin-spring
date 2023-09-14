package kr.pincoin.api.shop.domain;

import jakarta.persistence.*;
import kr.pincoin.api.home.domain.BaseDateTime;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Table(name = "category")
@Getter
public class Category extends BaseDateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "slug")
    private String slug;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "description")
    private String description;

    @Column(name = "description1")
    private String description1;
}
