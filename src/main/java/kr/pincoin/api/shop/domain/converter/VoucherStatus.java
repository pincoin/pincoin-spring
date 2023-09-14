package kr.pincoin.api.shop.domain.converter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum VoucherStatus {
    PURCHASED(0, "매입"),
    SOLD(1, "판매"),
    REVOKED(1, "발권취소");

    private final Integer code;
    private final String description;

    VoucherStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static VoucherStatus fromString(String description) {
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
