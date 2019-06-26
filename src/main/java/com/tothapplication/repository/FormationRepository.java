package com.tothapplication.repository;

import com.tothapplication.domain.Formation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Formation entity.
 */
@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {

    @Query(value = "select distinct formation from Formation formation left join fetch formation.ccps",
        countQuery = "select count(distinct formation) from Formation formation")
    Page<Formation> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct formation from Formation formation left join fetch formation.ccps")
    List<Formation> findAllWithEagerRelationships();

    @Query("select formation from Formation formation left join fetch formation.ccps where formation.id =:id")
    Optional<Formation> findOneWithEagerRelationships(@Param("id") Long id);

}
