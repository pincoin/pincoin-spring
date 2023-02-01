package kr.pincoin.be.django.repository;

import kr.pincoin.be.django.domain.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Long> {
}
