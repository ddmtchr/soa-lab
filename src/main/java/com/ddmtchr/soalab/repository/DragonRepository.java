package com.ddmtchr.soalab.repository;

import com.ddmtchr.soalab.dto.dragon.DragonType;
import com.ddmtchr.soalab.dto.dragon.DragonTypeCountDto;
import com.ddmtchr.soalab.entity.Dragon;
import com.ddmtchr.soalab.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DragonRepository extends JpaRepository<Dragon, Long>, JpaSpecificationExecutor<Dragon> {

    Optional<Dragon> findFirstByOrderByNameAsc();

    @Query("SELECT d.type, COUNT(d) FROM Dragon d GROUP BY d.type")
    List<DragonTypeCountDto> countAllByTypes();

    long countByTypeGreaterThan(DragonType type);

    List<Dragon> findAllByKiller(Person person);
}
