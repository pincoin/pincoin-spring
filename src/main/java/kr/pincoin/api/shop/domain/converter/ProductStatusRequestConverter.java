package kr.pincoin.api.shop.domain.converter;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

import java.util.stream.Stream;

public class ProductStatusRequestConverter implements Converter<String, ProductStatus> {
    @Override
    public ProductStatus convert(@NonNull String description) {
        return Stream.of(ProductStatus.values())
                .filter(c -> c.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 제품 상태가 없습니다."));
    }
}
