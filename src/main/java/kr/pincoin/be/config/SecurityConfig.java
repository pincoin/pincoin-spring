package kr.pincoin.be.config;

import kr.pincoin.be.auth.util.DjangoPasswordEncoder;
import kr.pincoin.be.member.jwt.JwtAccessDeniedHandler;
import kr.pincoin.be.member.jwt.JwtAuthenticationEntryPoint;
import kr.pincoin.be.member.jwt.JwtFilter;
import kr.pincoin.be.member.service.MemberDetailsService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Getter
@Slf4j
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 인증 API

        // 폼 로그인 인증
        http.formLogin().disable();

        // HTTP Basic 인증
        http.httpBasic().disable();

        // CSRF
        // http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));
        http.csrf().disable();

        // CORS
        // corsConfigurationSource 이름의 빈을 기본값으로 사용
        http.cors(withDefaults());

        // 예외처리
        http.exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()) // 401 Unauthorized
                .accessDeniedHandler(new JwtAccessDeniedHandler()); // 403 Forbidden

        // HTTP 프로토콜 헤더
        http.headers(headers -> {
            // Strict-Transport-Security: max-age=31536000 ; includeSubDomains ; preload
            headers.httpStrictTransportSecurity()
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000)
                    .preload(true)
                    //X-XSS-Protection: 1; mode=block
                    .and().xssProtection().headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                    // Cache-Control: no-cache, no-store, max-age=0, must-revalidate
                    // Pragma: no-cache
                    // Expires: 0
                    .and().cacheControl()
                    // X-Content-Type-Options: nosniff
                    .and().contentSecurityPolicy(
                            "default-src 'self'; style-src 'self' 'unsafe-inline' maxcdn.bootstrapcdn.com getbootstrap.com;")
                    .and().contentTypeOptions()
                    // X-Frame-Options: SAMEORIGIN | DENY
                    .and().frameOptions().sameOrigin();
        });

        // 세션관리
        http.sessionManagement(session ->
                                       session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                               .maximumSessions(1).maxSessionsPreventsLogin(true));

        // 요청 리소스 권한 매핑
        http.authorizeHttpRequests(auth ->
                                           // 인가 API 예시
                                           // requestMatchers().hasRole().permitAll()
                                           // requestMatchers().permitAll()
                                           // requestMatchers().denyAll()
                                           // anyRequest().authenticated()
                                           // anyRequest().fullyAuthenticated() // 인증된 사용자만 접근 가능 단, rememberMe 이용 시 접근 불가
                                           auth
                                                   .requestMatchers("/").permitAll()
                                                   .requestMatchers("/auth/**").permitAll()
                                                   .anyRequest().fullyAuthenticated()
                                  );

        // JWT 토큰 처리 필터 추가
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // 생략
        // http.logout()
        // http.rememberMe()

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new MemberDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new DjangoPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
