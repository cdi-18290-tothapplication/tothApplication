package com.tothapplication.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A FormationSession.
 */
@Entity
@Table(name = "formation_session")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FormationSession implements Serializable {

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

    @OneToMany(mappedBy = "formationSession")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Evaluation> evaluations = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "formation_session_studients",
               joinColumns = @JoinColumn(name = "formation_session_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "studients_id", referencedColumnName = "id"))
    private Set<Studient> studients = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "formation_session_documents",
               joinColumns = @JoinColumn(name = "formation_session_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "documents_id", referencedColumnName = "id"))
    private Set<Document> documents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("sessions")
    private Formation formation;

    @OneToMany(mappedBy = "formationSession")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Intervention> interventions = new HashSet<>();

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

    public FormationSession begin(LocalDate begin) {
        this.begin = begin;
        return this;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public FormationSession end(LocalDate end) {
        this.end = end;
        return this;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Set<Evaluation> getEvaluations() {
        return evaluations;
    }

    public FormationSession evaluations(Set<Evaluation> evaluations) {
        this.evaluations = evaluations;
        return this;
    }

    public FormationSession addEvaluation(Evaluation evaluation) {
        this.evaluations.add(evaluation);
        evaluation.setFormationSession(this);
        return this;
    }

    public FormationSession removeEvaluation(Evaluation evaluation) {
        this.evaluations.remove(evaluation);
        evaluation.setFormationSession(null);
        return this;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public Set<Studient> getStudients() {
        return studients;
    }

    public FormationSession studients(Set<Studient> studients) {
        this.studients = studients;
        return this;
    }

    public FormationSession addStudients(Studient studient) {
        this.studients.add(studient);
        return this;
    }

    public FormationSession removeStudients(Studient studient) {
        this.studients.remove(studient);
        return this;
    }

    public void setStudients(Set<Studient> studients) {
        this.studients = studients;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public FormationSession documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public FormationSession addDocuments(Document document) {
        this.documents.add(document);
        return this;
    }

    public FormationSession removeDocuments(Document document) {
        this.documents.remove(document);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public Formation getFormation() {
        return formation;
    }

    public FormationSession formation(Formation formation) {
        this.formation = formation;
        return this;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Set<Intervention> getInterventions() {
        return interventions;
    }

    public FormationSession interventions(Set<Intervention> interventions) {
        this.interventions = interventions;
        return this;
    }

    public FormationSession addIntervention(Intervention intervention) {
        this.interventions.add(intervention);
        intervention.setFormationSession(this);
        return this;
    }

    public FormationSession removeIntervention(Intervention intervention) {
        this.interventions.remove(intervention);
        intervention.setFormationSession(null);
        return this;
    }

    public void setInterventions(Set<Intervention> interventions) {
        this.interventions = interventions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormationSession)) {
            return false;
        }
        return id != null && id.equals(((FormationSession) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FormationSession{" +
            "id=" + getId() +
            ", begin='" + getBegin() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
