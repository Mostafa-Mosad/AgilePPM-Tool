package io.agile.ppmtool.dto;

import io.agile.ppmtool.models.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectDTOMapper {

    public ProjectDTO mapEntityToDto(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setProjectName(project.getProjectName());
        projectDTO.setDescription(project.getDescription());
        return projectDTO;
    }

    public Project mapDtoToEntity(ProjectDTO projectDTO, Project project) {
        project.setProjectName(projectDTO.getProjectName());
        project.setDescription(projectDTO.getDescription());
        return project;
    }
}
