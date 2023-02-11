package kr.pincoin.be.django.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "django_migrations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Migrations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app")
    @NotNull
    @NotBlank
    private String app;

    @Column(name = "name")
    @NotNull
    @NotBlank
    private String name;

    @Column(name = "applied")
    @NotNull
    private LocalDateTime applied;

    public Migrations(String app, String name) {
        this.app = app;
        this.name = name;

        this.applied = LocalDateTime.now();
    }
}
