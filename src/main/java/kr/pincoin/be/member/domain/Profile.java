package kr.pincoin.be.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.pincoin.be.auth.domain.User;
import kr.pincoin.be.home.domain.BaseDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "member_profile")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseDateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;

    private String address;

    private boolean phoneVerified;

    private int phoneVerifiedStatus;

    private boolean documentVerified;

    private boolean allowOrder;

    private String photoId;

    private String card;

    private Long totalOrderCount;

    private LocalDateTime firstPurchased;

    private LocalDateTime lastPurchased;

    private boolean notPurchasedMonths;

    private LocalDateTime repurchased;

    private BigDecimal maxPrice;

    private BigDecimal totalListPrice;

    private BigDecimal totalSellingPrice;

    private BigDecimal averagePrice;

    private BigDecimal mileage;

    private String memo;

    private LocalDate dateOfBirth;

    private int gender;

    private int domestic;

    private String telecom;

    @OneToOne(optional = false,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;
}
