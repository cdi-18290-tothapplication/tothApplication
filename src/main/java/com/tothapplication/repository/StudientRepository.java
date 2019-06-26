package com.tothapplication.repository;

import com.tothapplication.domain.Studient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Studient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudientRepository extends JpaRepository<Studient, Long> {

}
