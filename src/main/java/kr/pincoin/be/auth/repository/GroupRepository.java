package kr.pincoin.be.auth.repository;

import kr.pincoin.be.auth.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Override
    List<Group> findAll();
}
