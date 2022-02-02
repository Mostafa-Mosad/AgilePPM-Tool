package io.agile.ppmtool.dto;

import io.agile.ppmtool.models.Project;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
public class ProjectDTO {

    @Id
    Long id;
    @NotBlank
    String projectName;
    @NotBlank
    String description;

}
