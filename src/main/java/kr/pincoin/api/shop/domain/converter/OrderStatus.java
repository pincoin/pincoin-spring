package kr.pincoin.api.shop.domain.converter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum OrderStatus {
    PAYMENT_PENDING(0, "입금확인중"),
    PAYMENT_COMPLETED(1, "입금완료"),
    UNDER_REVIEW(2, "인증심사중"),
    PAYMENT_VERIFIED(3, "입금인증완료"),
    SHIPPED(4, "발송완료"),
    REFUND_REQUESTED(5, "환불요청"),
    REFUND_PENDING(6, "환불대기"),
    REFUNDED1(7, "환불완료"),
    REFUNDED2(8, "환불완료"),
    VOIDED(9, "주문무효");

    private final Integer code;
    private final String description;

    OrderStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static OrderStatus fromString(String description) {
        return Stream.of(values())
                .filter(c -> c.getDescription().equals(description))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
