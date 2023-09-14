package kr.pincoin.api.config;

import kr.pincoin.api.shop.domain.converter.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // @RequestParam 에 컨버터 사용 시
        registry.addConverter(new OrderStatusRequestConverter());
        registry.addConverter(new OrderVisibilityRequestConverter());
        registry.addConverter(new PaymentMethodRequestConverter());
        registry.addConverter(new ProductStatusRequestConverter());
        registry.addConverter(new VoucherStatusRequestConverter());
    }
}
