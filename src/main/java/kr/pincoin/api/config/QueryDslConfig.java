package kr.pincoin.api.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        // 동시성 문제
        // JPAQueryFactory 객체 생성할 때 EntityManager 의존성 주입
        // EntityManager 객체는 트랜잭션 단위로 분리되어 동작하므로 동시성 문제가 없음
        return new JPAQueryFactory(entityManager);
    }
}
