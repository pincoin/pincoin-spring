package kr.pincoin.api.shop.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter
public class PaymentMethodConverter implements AttributeConverter<PaymentMethod, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            return null;
        }

        return paymentMethod.getCode();
    }

    @Override
    public PaymentMethod convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }

        return Stream.of(PaymentMethod.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
