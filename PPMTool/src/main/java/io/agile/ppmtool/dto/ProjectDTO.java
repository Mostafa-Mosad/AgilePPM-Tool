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

    public static ProjectDTO mapEntityToDto(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setProjectName(project.getProjectName());
        projectDTO.setDescription(project.getDescription());
        return projectDTO;
    }

    public static Project mapDtoToEntity(ProjectDTO projectDTO, Project project) {
        project.setProjectName(projectDTO.getProjectName());
        project.setDescription(projectDTO.getDescription());
        return project;
    }
}
