package com.tothapplication.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tothapplication.domain.Studient} entity.
 */
public class StudientDTO implements Serializable {

    private Long id;

    private LocalDate birthdate;


    private Long userId;

    private Long photoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long documentId) {
        this.photoId = documentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudientDTO studientDTO = (StudientDTO) o;
        if (studientDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studientDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudientDTO{" +
            "id=" + getId() +
            ", birthdate='" + getBirthdate() + "'" +
            ", user=" + getUserId() +
            ", photo=" + getPhotoId() +
            "}";
    }
}
