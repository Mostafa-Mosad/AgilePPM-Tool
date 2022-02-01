package io.agile.ppmtool.services;

import io.agile.ppmtool.exceptions.ProjectIdentifierException;
import io.agile.ppmtool.exceptions.ProjectNotFoundException;
import io.agile.ppmtool.models.Project;
import io.agile.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

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
}
