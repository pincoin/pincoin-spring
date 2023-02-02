package kr.pincoin.be.member.dto;

import kr.pincoin.be.django.domain.ContentType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PermissionResponse {
    private String name;

    private ContentType contentType;

    private String codename;

    public PermissionResponse(String name, ContentType contentType, String codename) {
        this.name = name;
        this.contentType = contentType;
        this.codename = codename;
    }
}
