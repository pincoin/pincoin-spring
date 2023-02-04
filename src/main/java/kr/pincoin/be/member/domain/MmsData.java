package kr.pincoin.be.member.domain;

import jakarta.persistence.*;
import kr.pincoin.be.home.domain.BaseDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name="MmsData")
@Table(name = "member_mmsdata")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MmsData extends BaseDateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
