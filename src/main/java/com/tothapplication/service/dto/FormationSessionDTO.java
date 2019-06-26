package com.tothapplication.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.tothapplication.domain.FormationSession} entity.
 */
public class FormationSessionDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate begin;

    @NotNull
    private LocalDate end;


    private Set<StudientDTO> studients = new HashSet<>();

    private Set<DocumentDTO> documents = new HashSet<>();

    private Long formationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Set<StudientDTO> getStudients() {
        return studients;
    }

    public void setStudients(Set<StudientDTO> studients) {
        this.studients = studients;
    }

    public Set<DocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<DocumentDTO> documents) {
        this.documents = documents;
    }

    public Long getFormationId() {
        return formationId;
    }

    public void setFormationId(Long formationId) {
        this.formationId = formationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormationSessionDTO formationSessionDTO = (FormationSessionDTO) o;
        if (formationSessionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formationSessionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormationSessionDTO{" +
            "id=" + getId() +
            ", begin='" + getBegin() + "'" +
            ", end='" + getEnd() + "'" +
            ", formation=" + getFormationId() +
            "}";
    }
}
