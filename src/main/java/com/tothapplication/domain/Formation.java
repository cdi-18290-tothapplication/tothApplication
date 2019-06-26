package com.tothapplication.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Formation.
 */
@Entity
@Table(name = "formation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Formation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "jhi_desc")
    private String desc;

    @OneToMany(mappedBy = "formation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FormationSession> sessions = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "formation_ccp",
               joinColumns = @JoinColumn(name = "formation_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ccp_id", referencedColumnName = "id"))
    private Set<CCP> ccps = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public Formation label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDesc() {
        return desc;
    }

    public Formation desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Set<FormationSession> getSessions() {
        return sessions;
    }

    public Formation sessions(Set<FormationSession> formationSessions) {
        this.sessions = formationSessions;
        return this;
    }

    public Formation addSessions(FormationSession formationSession) {
        this.sessions.add(formationSession);
        formationSession.setFormation(this);
        return this;
    }

    public Formation removeSessions(FormationSession formationSession) {
        this.sessions.remove(formationSession);
        formationSession.setFormation(null);
        return this;
    }

    public void setSessions(Set<FormationSession> formationSessions) {
        this.sessions = formationSessions;
    }

    public Set<CCP> getCcps() {
        return ccps;
    }

    public Formation ccps(Set<CCP> cCPS) {
        this.ccps = cCPS;
        return this;
    }

    public Formation addCcp(CCP cCP) {
        this.ccps.add(cCP);
        return this;
    }

    public Formation removeCcp(CCP cCP) {
        this.ccps.remove(cCP);
        return this;
    }

    public void setCcps(Set<CCP> cCPS) {
        this.ccps = cCPS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formation)) {
            return false;
        }
        return id != null && id.equals(((Formation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Formation{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", desc='" + getDesc() + "'" +
            "}";
    }
}
