package kr.pincoin.be.member.repository;

import kr.pincoin.be.member.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
