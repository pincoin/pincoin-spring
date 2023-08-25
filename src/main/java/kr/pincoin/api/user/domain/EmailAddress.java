package kr.pincoin.api.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account_emailaddress")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "verified")
    @NotNull
    private Boolean verified;

    @Column(name = "primary")
    @NotNull
    private Boolean primary;

    @ManyToOne(optional = false,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    public EmailAddress(String email,
                        @NotNull Boolean verified,
                        @NotNull Boolean primary,
                        @NotNull User user) {
        this.email = email;
        this.verified = verified;
        this.primary = primary;
        this.user = user;
    }

    public EmailAddress validate() {
        this.verified = true;
        return this;
    }

    public EmailAddress invalidate() {
        this.verified = false;
        return this;
    }

    public EmailAddress makePrimary() {
        this.primary = true;
        return this;
    }

    public EmailAddress makeSecondary() {
        this.primary = false;
        return this;
    }
}