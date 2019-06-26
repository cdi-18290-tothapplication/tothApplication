package com.tothapplication.repository;

import com.tothapplication.domain.Intervention;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Intervention entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {

}
