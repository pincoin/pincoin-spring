package kr.pincoin.api.shop.domain.converter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum PaymentMethod {
    BANK_TRANSFER(0, "계좌이체 / 무통장입금"),
    ESCROW(1, "에스크로 (KB)"),
    PAYPAL(2, "페이팔 (PayPal)"),
    CREDIT_CARD(3, "신용카드"),
    BANK_TRANSFER_PG(4, "계좌이체"),
    VIRTUAL_ACCOUNT(5, "가상계좌"),
    PHONE_BILL(6, "휴대폰소액결제");

    private final Integer code;
    private final String description;

    PaymentMethod(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PaymentMethod fromString(String description) {
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
