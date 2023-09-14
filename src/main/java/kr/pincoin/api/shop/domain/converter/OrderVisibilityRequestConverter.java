package kr.pincoin.api.shop.domain.converter;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

import java.util.stream.Stream;

public class OrderVisibilityRequestConverter implements Converter<String, OrderVisibility> {
    @Override
    public OrderVisibility convert(@NonNull String description) {
        return Stream.of(OrderVisibility.values())
                .filter(c -> c.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 주문 노출여부가 없습니다."));
    }
}
