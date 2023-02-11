package kr.pincoin.be.django.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "django_content_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app_label")
    @NotNull
    @NotBlank
    private String appLabel;

    @Column(name = "model")
    @NotNull
    @NotBlank
    private String model;

    public ContentType(String appLabel, String model) {
        this.appLabel = appLabel;
        this.model = model;
    }
}
