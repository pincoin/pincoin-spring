package kr.pincoin.api.shop.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter
public class OrderVisibilityConverter implements AttributeConverter<OrderVisibility, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrderVisibility orderVisibility) {
        if (orderVisibility == null) {
            return null;
        }

        return orderVisibility.getCode();
    }

    @Override
    public OrderVisibility convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }

        return Stream.of(OrderVisibility.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
