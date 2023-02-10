package kr.pincoin.be.member.repository;

import kr.pincoin.be.member.domain.RedisRefreshToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

import java.util.Optional;

@DataRedisTest
@MockBean(JpaMetamodelMappingContext.class) // JPA Auditing 적용 안 함
class RedisRefreshTokenRepositoryTest {

    @Autowired
    private RedisRefreshTokenRepository redisRefreshTokenRepository;

    @Test
    void 토큰조회() {
        Optional<RedisRefreshToken> byRefreshToken = redisRefreshTokenRepository.findByRefreshToken(
                "11df730823c5472c9626667235d72a2c");
    }
}