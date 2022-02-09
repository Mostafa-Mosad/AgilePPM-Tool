package io.agile.ppmtool.dto;

import io.agile.ppmtool.models.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    Long id;
    String projectName;
    String description;

}
