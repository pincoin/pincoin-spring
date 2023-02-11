package kr.pincoin.be.django.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.pincoin.be.auth.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "django_admin_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action_time")
    @NotNull
    private LocalDateTime actionTime;

    @Column(name = "object_id")
    @NotNull
    @NotBlank
    private String objectId;

    @Column(name = "object_repr")
    @NotNull
    @NotBlank
    private String objectRepr;

    @Column(name = "action_flag")
    @NotNull
    private int actionFlag;

    @Column(name = "change_message")
    @NotNull
    @NotBlank
    private String changeMessage;

    @ManyToOne(optional = false, // 내부(inner join)
            fetch = FetchType.LAZY)
    @JoinColumn(name = "content_type_id")
    @NotNull
    private ContentType contentType;

    @ManyToOne(optional = false, // 내부(inner join)
            fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    public AdminLog(String objectId,
                    String objectRepr,
                    int actionFlag,
                    String changeMessage,
                    ContentType contentType,
                    User user) {
        this.objectId = objectId;
        this.objectRepr = objectRepr;
        this.actionFlag = actionFlag;
        this.changeMessage = changeMessage;
        this.contentType = contentType;
        this.user = user;

        this.actionTime = LocalDateTime.now();
    }
}
