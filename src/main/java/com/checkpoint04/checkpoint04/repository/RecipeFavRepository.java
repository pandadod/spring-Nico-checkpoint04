package com.checkpoint04.checkpoint04.repository;

import com.checkpoint04.checkpoint04.entity.RecipeFav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeFavRepository extends JpaRepository<RecipeFav, Long> {

    List<RecipeFav> findByNameContaining(String name);
}
