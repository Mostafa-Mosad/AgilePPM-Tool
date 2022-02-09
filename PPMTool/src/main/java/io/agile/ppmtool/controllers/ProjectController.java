package io.agile.ppmtool.controllers;

import io.agile.ppmtool.dto.ProjectDTO;
import io.agile.ppmtool.exceptions.ArgumentNotValidException;
import io.agile.ppmtool.models.Project;
import io.agile.ppmtool.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/api/project")
@Slf4j
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/createproject")
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        log.info("ProjectController - createProject: Saving a new project: {}", project);
        Project savedProject = projectService.createProject(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    @GetMapping("/getprojectbyidentifier/{projectIdentifier}")
    public ResponseEntity<Project> getProjectByIdentifier(@PathVariable String projectIdentifier) {
        log.info("ProjectController - getProjectByIdentifier: Get project by identifier '{}'", projectIdentifier);
        Project project = projectService.getProjectByIdentifier(projectIdentifier);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/getallprojects")
    public ResponseEntity<List<Project>> getAllProjects() {
        log.info("ProjectController - Get all projects");
        List<Project> projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PutMapping("/updateproject/{projectId}")
    public ResponseEntity<Project> updateProject(@Valid @RequestBody ProjectDTO projectDTO, @PathVariable Long projectId) {
        log.info("ProjectController - updateProject: Updating an existing project with id: {}, updated data: {}", projectId, projectDTO);
        Project updatedProject = projectService.updateProjectById(projectId, projectDTO);
        log.info("ProjectController - updateProject: After updating project: {}", updatedProject);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/deleteprojectbyid/{projectId}")
    public ResponseEntity<Object> deleteProjectById(@PathVariable Long projectId) {
        log.info("ProjectController - deleteProjectById: Deleting Project by id '{}'", projectId);
        projectService.deleteProjectById(projectId);
        return new ResponseEntity<>("Project was deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deleteprojectbyidentifier/{projectIdentifier}")
    public ResponseEntity<Object> deleteProjectByIdentifier(@PathVariable String projectIdentifier) {
        log.info("ProjectController - deleteProjectByIdentifier: Deleting project by identifier '{}'", projectIdentifier);
        projectService.deleteProjectByIdentifier(projectIdentifier);
        return new ResponseEntity<>("Project was deleted successfully", HttpStatus.OK);
    }
}
