package io.agile.ppmtool.services;

import io.agile.ppmtool.dto.ProjectDTO;
import io.agile.ppmtool.dto.ProjectDTOMapper;
import io.agile.ppmtool.exceptions.ProjectIdentifierException;
import io.agile.ppmtool.exceptions.ProjectNotFoundException;
import io.agile.ppmtool.models.Project;
import io.agile.ppmtool.repositories.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProjectService {

    private ProjectRepository projectRepository;
    private ProjectDTOMapper projectDTOMapper;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ProjectDTOMapper projectDTOMapper) {
        this.projectRepository = projectRepository;
        this.projectDTOMapper = projectDTOMapper;
    }

    public Project createProject(Project project) {
        try {
            log.info("ProjectService - createProject: Saving a new project: {}", project);
            return projectRepository.save(project);
        }
        catch (Exception ex) {
            log.error("ProjectService - createProject: Error while saving a new project: {}", project);
            throw new ProjectIdentifierException();
        }
    }

    public Project getProjectByIdentifier(String projectIdentifier) {
        try {
            log.info("ProjectService - getProjectByIdentifier: Getting a project with identifier '{}'", projectIdentifier);
            Project project = projectRepository.getProjectByProjectIdentifier(projectIdentifier).get();
            return project;
        }
        catch (Exception ex) {
            log.error("ProjectService - getProjectByIdentifier: Error while getting a project with identifier '{}'", projectIdentifier);
            throw new ProjectNotFoundException("No Project Found with identifier:" + projectIdentifier);
        }
    }

    public List<Project> getAllProjects() {
        log.info("ProjectService - getAllProjects: Getting all projects");
        return projectRepository.findAll();
    }

    public Project updateProjectById(Long projectId, ProjectDTO projectDTO) {
        try{
            log.info("ProjectService - updateProjectById: Updating an existing project with a new data: {}", projectDTO);
            Project savedProject = projectRepository.findById(projectId).get();
            log.info("ProjectService - updateProjectById: Updating an existing project: {}", savedProject);
            Project updatedProject = projectDTOMapper.mapDtoToEntity(projectDTO, savedProject);
            log.info("ProjectService - updateProjectById: After updating the existing project, the updated project: {}", updatedProject);
            return projectRepository.save(updatedProject);
        }
        catch (Exception ex) {
            log.error("ProjectService - updateProjectById: Error while updating an existing project with a new data: {}", projectDTO);
            throw new ProjectNotFoundException("No Project Found with id:" + projectId);
        }
    }

    public void deleteProjectById(Long projectId) {
        try{
            log.info("ProjectService - deleteProjectById: Deleting project by id '{}'", projectId);
            Project project = projectRepository.findById(projectId).get();
            log.info("ProjectService - deleteProjectById: The project will be deleted: {}", project);
            projectRepository.deleteById(projectId);
        }
        catch (Exception ex) {
            log.error("ProjectService - deleteProjectById: Error while deleting project by id '{}'", projectId);
            throw new ProjectNotFoundException("No Project Found with id:" + projectId);
        }
    }

    public void deleteProjectByIdentifier(String projectIdentifier) {
        try{
            log.info("ProjectService - deleteProjectByIdentifier: Deleting project by identifier '{}'", projectIdentifier);
            Project project = projectRepository.getProjectByProjectIdentifier(projectIdentifier).get();
            log.info("ProjectService - deleteProjectByIdentifier: The project will be deleted: {}", project);
            projectRepository.delete(project);
        }
        catch (Exception ex) {
            log.error("ProjectService - deleteProjectByIdentifier: Error while deleting the project by identifier '{}'", projectIdentifier);
            throw new ProjectNotFoundException("No Project Found with identifier:" + projectIdentifier);
        }
    }
}
