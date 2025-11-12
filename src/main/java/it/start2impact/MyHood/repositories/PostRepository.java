package it.start2impact.MyHood.repositories;

import it.start2impact.MyHood.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    @Query("SELECT ALL FROM post WHERE post_date BETWEEN :fromDate AND :toDate;")
    public List<PostEntity> findByDate(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
}
