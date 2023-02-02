package kr.pincoin.be.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupResponse {
    private String name;

    public GroupResponse(String name) {
        this.name = name;
    }
}
