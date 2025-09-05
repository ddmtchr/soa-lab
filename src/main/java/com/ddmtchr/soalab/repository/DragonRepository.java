package com.ddmtchr.soalab.repository;

import com.ddmtchr.soalab.entity.Dragon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DragonRepository extends JpaRepository<Dragon, Long> {
}
