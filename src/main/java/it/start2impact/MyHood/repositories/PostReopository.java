package it.start2impact.MyHood.repositories;

import it.start2impact.MyHood.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReopository extends JpaRepository<PostEntity, Integer> {
}
