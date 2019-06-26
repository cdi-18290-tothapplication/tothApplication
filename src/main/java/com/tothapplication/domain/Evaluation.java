package com.tothapplication.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Evaluation.
 */
@Entity
@Table(name = "evaluation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Evaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "note", nullable = false)
    private Integer note;

    @Column(name = "commentary")
    private String commentary;

    @OneToOne
    @JoinColumn(unique = true)
    private Studient studient;

    @OneToOne
    @JoinColumn(unique = true)
    private Trainer trainer;

    @ManyToOne
    @JsonIgnoreProperties("evaluations")
    private FormationSession formationSession;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Evaluation date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getNote() {
        return note;
    }

    public Evaluation note(Integer note) {
        this.note = note;
        return this;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public String getCommentary() {
        return commentary;
    }

    public Evaluation commentary(String commentary) {
        this.commentary = commentary;
        return this;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Studient getStudient() {
        return studient;
    }

    public Evaluation studient(Studient studient) {
        this.studient = studient;
        return this;
    }

    public void setStudient(Studient studient) {
        this.studient = studient;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public Evaluation trainer(Trainer trainer) {
        this.trainer = trainer;
        return this;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public FormationSession getFormationSession() {
        return formationSession;
    }

    public Evaluation formationSession(FormationSession formationSession) {
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
        if (!(o instanceof Evaluation)) {
            return false;
        }
        return id != null && id.equals(((Evaluation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", note=" + getNote() +
            ", commentary='" + getCommentary() + "'" +
            "}";
    }
}
