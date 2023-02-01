package kr.pincoin.be.django.repository;

import kr.pincoin.be.django.domain.Migrations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MigrationsRepository extends JpaRepository<Migrations, Long> {
}
