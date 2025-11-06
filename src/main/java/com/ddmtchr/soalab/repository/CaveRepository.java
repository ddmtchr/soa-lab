package com.ddmtchr.soalab.repository;

import com.ddmtchr.soalab.entity.Cave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaveRepository extends JpaRepository<Cave, Long> {
}
