package kr.pincoin.api.shop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.pincoin.api.home.domain.BaseDateTime;
import kr.pincoin.api.shop.domain.converter.VoucherStatus;
import kr.pincoin.api.shop.domain.converter.VoucherStatusConverter;
import lombok.Getter;

@Entity
@Table(name = "voucher")
@Getter
public class Voucher extends BaseDateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "status")
    @NotNull
    @Convert(converter = VoucherStatusConverter.class)
    private VoucherStatus status;

    @ManyToOne(optional = false,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;
}
