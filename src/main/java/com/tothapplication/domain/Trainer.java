package com.tothapplication.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Trainer.
 */
@Entity
@Table(name = "trainer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Trainer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne(mappedBy = "trainer")
    @JsonIgnore
    private Evaluation evaluation;

    @OneToMany(mappedBy = "trainer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Intervention> interventions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Trainer user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public Trainer evaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public Set<Intervention> getInterventions() {
        return interventions;
    }

    public Trainer interventions(Set<Intervention> interventions) {
        this.interventions = interventions;
        return this;
    }

    public Trainer addIntervention(Intervention intervention) {
        this.interventions.add(intervention);
        intervention.setTrainer(this);
        return this;
    }

    public Trainer removeIntervention(Intervention intervention) {
        this.interventions.remove(intervention);
        intervention.setTrainer(null);
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
        if (!(o instanceof Trainer)) {
            return false;
        }
        return id != null && id.equals(((Trainer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Trainer{" +
            "id=" + getId() +
            "}";
    }
}
