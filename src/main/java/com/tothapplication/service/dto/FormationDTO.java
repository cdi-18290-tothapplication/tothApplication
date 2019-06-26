package com.tothapplication.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.tothapplication.domain.Formation} entity.
 */
public class FormationDTO implements Serializable {

    private Long id;

    @NotNull
    private String label;

    private String desc;


    private Set<CCPDTO> ccps = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Set<CCPDTO> getCcps() {
        return ccps;
    }

    public void setCcps(Set<CCPDTO> cCPS) {
        this.ccps = cCPS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormationDTO formationDTO = (FormationDTO) o;
        if (formationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormationDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", desc='" + getDesc() + "'" +
            "}";
    }
}
