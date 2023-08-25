package kr.pincoin.api.config;

import kr.pincoin.api.auth.jwt.JwtAccessDeniedHandler;
import kr.pincoin.api.auth.jwt.JwtAuthenticationEntryPoint;
import kr.pincoin.api.auth.jwt.JwtFilter;
import kr.pincoin.api.auth.password.DjangoPasswordEncoder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@Getter
@Slf4j
public class SecurityConfig {
    @Value("${security-config.content-security-policy}")
    private String contentSecurityPolicy;

    @Value("${security-config.cors.origins}")
    private String corsOrigins;

    @Value("${security-config.cors.headers}")
    private String corsHeaders;

    @Value("${security-config.cors.methods}")
    private String corsMethods;

    @Value("${security-config.cors.allow-credentials}")
    private boolean corsAllowCredentials;

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    // 과거: WebSecurityConfigurerAdapter 클래스를 상속 후 스프링 시큐리티 설정(deprecated)
    // 현재: 빈 설정을 통해 스프링 시큐리티 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 인증 API

        // 폼 로그인 인증 (사용 안 함)
        http.formLogin(AbstractHttpConfigurer::disable); // 버전 6.1
        // http.formLogin().disable(); // 버전 6.0

        // http.logout()
        // http.rememberMe()

        // HTTP Basic 인증 (사용 안 함)
        http.httpBasic(AbstractHttpConfigurer::disable);

        // CSRF
        // http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));
        http.csrf(AbstractHttpConfigurer::disable);

        // CORS
        // corsConfigurationSource 이름의 빈을 기본값으로 사용
        http.cors(withDefaults());

        // 예외처리
        http.exceptionHandling(config -> {
            config.authenticationEntryPoint(new JwtAuthenticationEntryPoint()) // 401 Unauthorized: 로그인 실패
                    .accessDeniedHandler(new JwtAccessDeniedHandler()); // 403 Forbidden: 권한 없음
        });

        // HTTP 프로토콜 헤더
        http.headers(headers -> {
            headers.defaultsDisabled();

            // Strict-Transport-Security: max-age=31536000 ; includeSubDomains ; preload
            headers.httpStrictTransportSecurity(hstsConfig -> hstsConfig
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000)
                    .preload(true));

            //X-XSS-Protection: 1; mode=block
            headers.xssProtection(xssConfig -> xssConfig.headerValue(
                    XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK));

            // Cache-Control: no-cache, no-store, max-age=0, must-revalidate
            // Pragma: no-cache
            // Expires: 0
            headers.cacheControl(cacheControlConfig -> {
            });

            // Content-Security-Policy: default-src 'none'
            headers.contentSecurityPolicy(contentSecurityPolicyConfig ->
                                                  contentSecurityPolicyConfig.policyDirectives(contentSecurityPolicy));

            // X-Content-Type-Options: nosniff
            headers.contentTypeOptions(contentTypeOptionsConfig -> {
            });

            // X-Frame-Options: SAMEORIGIN | DENY
            headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
        });

        // 세션관리
        // Stateless: 인증/인가 처리 관점에서 세션 생성하지 않음, (CSRF 외 보안기능에서는 세션 생성)
        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            session.maximumSessions(1).maxSessionsPreventsLogin(true);
        });

        // 요청 리소스 권한 매핑
        http.authorizeHttpRequests(auth -> auth
                                           // 인가 API 예시
                                           // requestMatchers().hasRole().permitAll()
                                           // requestMatchers().denyAll()
                                           .requestMatchers("/").permitAll()
                                           .requestMatchers("/auth/**").permitAll()
                                           .requestMatchers("/users/**").permitAll()
                                           .requestMatchers("/categories/**").permitAll()
                                           .requestMatchers("/orders/**").permitAll()
                                           .requestMatchers("/aligo/**").permitAll()
                                           .requestMatchers("/line-notify/**").permitAll()
                                           // anyRequest().authenticated() - rememberMe 로그인 허용
                                           .anyRequest().fullyAuthenticated() //rememberMe 허용 안 함
                                  );

        // JWT 토큰 처리 필터 추가
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new DjangoPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern(corsOrigins);
        configuration.addAllowedHeader(corsHeaders);
        configuration.addAllowedMethod(corsMethods);
        configuration.setAllowCredentials(corsAllowCredentials);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}