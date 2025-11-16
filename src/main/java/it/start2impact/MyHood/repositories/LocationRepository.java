package it.start2impact.MyHood.repositories;

import it.start2impact.MyHood.entities.LocationEntity;
import it.start2impact.MyHood.entities.PostEntity;
import it.start2impact.MyHood.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Integer> {

    Optional<LocationEntity> findByLocationIgnoreCase(String location);

}
