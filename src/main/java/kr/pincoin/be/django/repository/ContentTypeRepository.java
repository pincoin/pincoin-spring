package kr.pincoin.be.django.repository;

import kr.pincoin.be.django.domain.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentTypeRepository extends JpaRepository<ContentType, Long> {
}
