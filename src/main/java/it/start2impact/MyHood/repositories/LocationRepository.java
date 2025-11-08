package it.start2impact.MyHood.repositories;

import it.start2impact.MyHood.entities.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Integer> {

    Optional<LocationEntity> findByLocationIgnoreCase(String location);
}
