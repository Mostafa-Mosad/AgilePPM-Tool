package io.agile.ppmtool.services;

import io.agile.ppmtool.dto.ProjectDTO;
import io.agile.ppmtool.exceptions.ProjectIdentifierException;
import io.agile.ppmtool.exceptions.ProjectNotFoundException;
import io.agile.ppmtool.models.Project;
import io.agile.ppmtool.repositories.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project) {
        try {
            return projectRepository.save(project);
        }
        catch (Exception ex) {
            throw new ProjectIdentifierException();
        }
    }

    public Project getProjectByIdentifier(String projectIdentifier) {
        try {
            Project project = projectRepository.getProjectByProjectIdentifier(projectIdentifier).get();
            return project;
        }
        catch (Exception ex) {
            throw new ProjectNotFoundException();
        }
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project updateProjectById(Long projectId, Project project) {
        try{
            Project savedProject = projectRepository.findById(projectId).get();
            ProjectDTO projectDTO = ProjectDTO.mapEntityToDto(project);
            Project updatedProject = ProjectDTO.mapDtoToEntity(projectDTO, savedProject);
            return projectRepository.save(updatedProject);
        }
        catch (Exception ex) {
            throw new ProjectNotFoundException();
        }
    }

    public void deleteProjectById(Long projectId) {
        try{
            Project project = projectRepository.findById(projectId).get();
            projectRepository.deleteById(projectId);
        }
        catch (Exception ex) {
            throw new ProjectNotFoundException();
        }
    }

    public void deleteProjectByIdentifier(String projectIdentifier) {
        try{
            Project project = projectRepository.getProjectByProjectIdentifier(projectIdentifier).get();
            projectRepository.delete(project);
        }
        catch (Exception ex) {
            throw new ProjectNotFoundException();
        }
    }
}
