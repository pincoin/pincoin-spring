package kr.pincoin.be.django.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "django_session")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Session {
    @Id
    private String sessionKey;

    @Column(name = "session_data")
    @NotNull
    @NotBlank
    private String sessionData;

    @Column(name = "expire_date")
    @NotNull
    private LocalDateTime expireDate;

    public Session(String sessionData) {
        this.sessionData = sessionData;
        this.expireDate = LocalDateTime.now().plusDays(14);
    }
}