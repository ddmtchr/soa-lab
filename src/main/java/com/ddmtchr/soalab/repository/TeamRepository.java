package com.ddmtchr.soalab.repository;

import com.ddmtchr.soalab.entity.Cave;
import com.ddmtchr.soalab.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findAllByOrderByIdAsc();

    List<Team> findAllByCave(Cave cave);
}
