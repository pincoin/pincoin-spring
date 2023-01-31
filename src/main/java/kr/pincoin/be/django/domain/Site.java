package kr.pincoin.be.django.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "django_site")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String domain;

    @NotNull
    @NotBlank
    private String name;

    public Site(String domain, String name) {
        this.domain = domain;
        this.name = name;
    }
}
