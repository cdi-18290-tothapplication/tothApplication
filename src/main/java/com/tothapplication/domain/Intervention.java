package com.tothapplication.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Intervention.
 */
@Entity
@Table(name = "intervention")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Intervention implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "begin", nullable = false)
    private LocalDate begin;

    @NotNull
    @Column(name = "jhi_end", nullable = false)
    private LocalDate end;

    @ManyToOne
    @JsonIgnoreProperties("interventions")
    private Trainer trainer;

    @ManyToOne
    @JsonIgnoreProperties("interventions")
    private FormationSession formationSession;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public Intervention begin(LocalDate begin) {
        this.begin = begin;
        return this;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public Intervention end(LocalDate end) {
        this.end = end;
        return this;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public Intervention trainer(Trainer trainer) {
        this.trainer = trainer;
        return this;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public FormationSession getFormationSession() {
        return formationSession;
    }

    public Intervention formationSession(FormationSession formationSession) {
        this.formationSession = formationSession;
        return this;
    }

    public void setFormationSession(FormationSession formationSession) {
        this.formationSession = formationSession;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Intervention)) {
            return false;
        }
        return id != null && id.equals(((Intervention) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Intervention{" +
            "id=" + getId() +
            ", begin='" + getBegin() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
