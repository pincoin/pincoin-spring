package kr.pincoin.api.shop.domain.converter;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

import java.util.stream.Stream;

public class PaymentMethodRequestConverter implements Converter<String, PaymentMethod> {
    @Override
    public PaymentMethod convert(@NonNull String description) {
        return Stream.of(PaymentMethod.values())
                .filter(c -> c.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 결제수단이 없습니다."));
    }
}
