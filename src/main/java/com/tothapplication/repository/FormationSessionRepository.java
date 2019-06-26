package com.tothapplication.repository;

import com.tothapplication.domain.FormationSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the FormationSession entity.
 */
@Repository
public interface FormationSessionRepository extends JpaRepository<FormationSession, Long> {

    @Query(value = "select distinct formationSession from FormationSession formationSession left join fetch formationSession.studients left join fetch formationSession.documents",
        countQuery = "select count(distinct formationSession) from FormationSession formationSession")
    Page<FormationSession> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct formationSession from FormationSession formationSession left join fetch formationSession.studients left join fetch formationSession.documents")
    List<FormationSession> findAllWithEagerRelationships();

    @Query("select formationSession from FormationSession formationSession left join fetch formationSession.studients left join fetch formationSession.documents where formationSession.id =:id")
    Optional<FormationSession> findOneWithEagerRelationships(@Param("id") Long id);

}
