package com.tothapplication.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tothapplication.domain.Evaluation} entity.
 */
public class EvaluationDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    private Integer note;

    private String commentary;


    private Long studientId;

    private Long trainerId;

    private Long formationSessionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Long getStudientId() {
        return studientId;
    }

    public void setStudientId(Long studientId) {
        this.studientId = studientId;
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

        EvaluationDTO evaluationDTO = (EvaluationDTO) o;
        if (evaluationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evaluationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EvaluationDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", note=" + getNote() +
            ", commentary='" + getCommentary() + "'" +
            ", studient=" + getStudientId() +
            ", trainer=" + getTrainerId() +
            ", formationSession=" + getFormationSessionId() +
            "}";
    }
}
