package kr.pincoin.be.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.pincoin.be.auth.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;

    private String refreshToken;

    @ManyToOne(optional = false,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    public Token(User user) {
        this.user = user;
    }

    public void issueAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void issueRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
