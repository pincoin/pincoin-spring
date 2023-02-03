package kr.pincoin.be.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.pincoin.be.auth.domain.User;
import kr.pincoin.be.home.domain.BaseDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static kr.pincoin.be.member.jwt.TokenProvider.REFRESH_TOKEN_EXPIRES_IN;

@Entity
@Table(name = "member_refreshtoken")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseDateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    private LocalDateTime expiresIn;

    @OneToOne(optional = false,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    public RefreshToken(User user) {
        this.user = user;
    }

    public RefreshToken issueRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;

        this.expiresIn = LocalDateTime.now().plus(Duration.of(REFRESH_TOKEN_EXPIRES_IN, ChronoUnit.SECONDS));

        return this;
    }
}
