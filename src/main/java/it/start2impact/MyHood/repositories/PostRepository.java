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

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    @Query("SELECT p FROM PostEntity p WHERE postDate BETWEEN :fromDate AND :toDate")
    List<PostEntity> findByDate(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);


    @Query("SELECT p FROM PostEntity p " +
            "WHERE (:from IS NULL OR p.postDate >= :from) " +
            "AND (:to IS NULL OR p.postDate <= :to) " +
            "AND (:eventType IS NULL OR p.eventType = :eventType) " +
            "AND (:location IS NULL OR p.userEntity.locationEntity = :location)")
    List<PostEntity> search(@Param("from") LocalDate from,
                            @Param("to") LocalDate to,
                            @Param("eventType") EventType eventType,
                            @Param("location") LocationEntity location);
}
