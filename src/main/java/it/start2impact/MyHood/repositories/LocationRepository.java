package it.start2impact.MyHood.repositories;

import it.start2impact.MyHood.entities.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Integer> {

    Optional<LocationEntity> findByLocationIgnoreCase(String location);

    @Query("SELECT l FROM LocationEntity l WHERE l.location like %:name%")
    List<LocationEntity> findByName(@Param(value = "name")String name);
}
