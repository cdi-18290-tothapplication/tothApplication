package com.tothapplication.repository;

import com.tothapplication.domain.CCP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CCP entity.
 */
@Repository
public interface CCPRepository extends JpaRepository<CCP, Long> {

    @Query(value = "select distinct cCP from CCP cCP left join fetch cCP.documents",
        countQuery = "select count(distinct cCP) from CCP cCP")
    Page<CCP> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct cCP from CCP cCP left join fetch cCP.documents")
    List<CCP> findAllWithEagerRelationships();

    @Query("select cCP from CCP cCP left join fetch cCP.documents where cCP.id =:id")
    Optional<CCP> findOneWithEagerRelationships(@Param("id") Long id);

}
