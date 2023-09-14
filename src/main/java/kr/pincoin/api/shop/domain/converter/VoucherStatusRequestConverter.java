package kr.pincoin.api.shop.domain.converter;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;

import java.util.stream.Stream;

public class VoucherStatusRequestConverter implements Converter<String, VoucherStatus> {
    @Override
    public VoucherStatus convert(@NonNull String description) {
        return Stream.of(VoucherStatus.values())
                .filter(c -> c.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 재고상태 없습니다."));
    }
}
