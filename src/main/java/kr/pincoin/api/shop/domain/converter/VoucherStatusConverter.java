package kr.pincoin.api.shop.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter
public class VoucherStatusConverter implements AttributeConverter<VoucherStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(VoucherStatus status) {
        if (status == null) {
            return null;
        }

        return status.getCode();
    }

    @Override
    public VoucherStatus convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }

        return Stream.of(VoucherStatus.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
