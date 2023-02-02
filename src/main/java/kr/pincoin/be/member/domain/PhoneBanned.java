package kr.pincoin.be.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_phonebanned")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneBanned extends BaseDateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
