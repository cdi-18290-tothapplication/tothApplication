package com.tothapplication.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tothapplication.domain.Intervention} entity.
 */
public class InterventionDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate begin;

    @NotNull
    private LocalDate end;


    private Long trainerId;

    private Long formationSessionId;

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

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public Long getFormationSessionId() {
        return formationSessionId;
    }

    public void setFormationSessionId(Long formationSessionId) {
        this.formationSessionId = formationSessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InterventionDTO interventionDTO = (InterventionDTO) o;
        if (interventionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), interventionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InterventionDTO{" +
            "id=" + getId() +
            ", begin='" + getBegin() + "'" +
            ", end='" + getEnd() + "'" +
            ", trainer=" + getTrainerId() +
            ", formationSession=" + getFormationSessionId() +
            "}";
    }
}
