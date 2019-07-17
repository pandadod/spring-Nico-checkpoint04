package com.checkpoint04.checkpoint04.repository;

import com.checkpoint04.checkpoint04.entity.RecipeFav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeFavRepository extends JpaRepository<RecipeFav, Long> {

    List<RecipeFav> findByNameContaining(String name);

    Optional<RecipeFav> findByName(String name);
}
