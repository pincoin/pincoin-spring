package kr.pincoin.api.shop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.pincoin.api.home.domain.BaseDateTime;
import kr.pincoin.api.shop.domain.converter.ProductStatus;
import kr.pincoin.api.shop.domain.converter.ProductStatusConverter;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Getter
public class Product extends BaseDateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "code")
    private String code;

    @Column(name = "list_price")
    private BigDecimal listPrice;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @Column(name = "is_pg_active")
    private Boolean isPgActive;

    @Column(name = "pg_selling_price")
    private BigDecimal pgSellingPrice;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @NotNull
    @Convert(converter = ProductStatusConverter.class)
    private ProductStatus status;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "is_in_stock")
    private Integer isInStock;

    @Column(name = "minimum_stock_level")
    private Integer minimumStockLevel;

    @Column(name = "maximum_stock_level")
    private Integer maximumStockLevel;

    @ManyToOne(optional = false,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @NotNull
    private Category category;
}
