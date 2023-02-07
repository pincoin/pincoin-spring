package kr.pincoin.be.auth.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.pincoin.be.django.domain.ContentType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name="Permission")
@Table(name = "auth_permission")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    @NotBlank
    private String name;

    @ManyToOne(optional = false,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "content_type_id")
    private ContentType contentType;

    @Column(name = "codename")
    @NotNull
    @NotBlank
    private String codename;

    public Permission(String name, ContentType contentType, String codename) {
        this.name = name;
        this.contentType = contentType;
        this.codename = codename;
    }
}
