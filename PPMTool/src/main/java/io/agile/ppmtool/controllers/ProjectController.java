package io.agile.ppmtool.controllers;

import io.agile.ppmtool.exceptions.ArgumentNotValidException;
import io.agile.ppmtool.models.Project;
import io.agile.ppmtool.services.ProjectService;
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
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/createproject")
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        Project savedProject = projectService.createProject(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    @GetMapping("/getprojectbyidentifier/{projectIdentifier}")
    public ResponseEntity<Project> getProjectByIdentifier(@PathVariable String projectIdentifier) {
        Project project = projectService.getProjectByIdentifier(projectIdentifier);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/getallprojects")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PutMapping("/updateproject/{projectId}")
    public ResponseEntity<Project> updateProject(@Valid @RequestBody Project project, @PathVariable Long projectId) {
        Project updatedProject = projectService.updateProjectById(projectId, project);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/deleteprojectbyid/{projectId}")
    public ResponseEntity<Object> deleteProjectById(@PathVariable Long projectId) {
        projectService.deleteProjectById(projectId);
        return new ResponseEntity<>("Project was deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deleteprojectbyidentifier/{projectIdentifier}")
    public ResponseEntity<Object> deleteProjectByIdentifier(@PathVariable String projectIdentifier) {
        projectService.deleteProjectByIdentifier(projectIdentifier);
        return new ResponseEntity<>("Project was deleted successfully", HttpStatus.OK);
    }
}
