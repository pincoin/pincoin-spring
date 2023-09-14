package kr.pincoin.api.shop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.pincoin.api.home.domain.BaseDateTime;
import kr.pincoin.api.shop.domain.converter.OrderStatus;
import kr.pincoin.api.shop.domain.converter.OrderStatusConverter;
import kr.pincoin.api.shop.domain.converter.OrderVisibility;
import kr.pincoin.api.user.domain.User;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Table(name = "order")
@Getter
public class Order extends BaseDateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_no", columnDefinition = "char")
    private String orderNo;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "accept_language")
    private String acceptLanguage;

    @Column(name = "ip_address", columnDefinition = "char")
    private String ipAddress;

    @Column(name = "payment_method")
    @NotNull
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus paymentMethod;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "status")
    @NotNull
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status;

    @Column(name = "is_visible")
    @NotNull
    @Convert(converter = OrderStatusConverter.class)
    private OrderVisibility isVisible;

    @Column(name = "total_list_price")
    private BigDecimal totalListPrice;

    @Column(name = "total_selling_price")
    private BigDecimal totalSellingPrice;

    @Column(name = "currency")
    private String currency;

    @Column(name = "message")
    private String message;

    @ManyToOne(optional = false,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne(optional = false,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @NotNull
    private Order parent;
}
