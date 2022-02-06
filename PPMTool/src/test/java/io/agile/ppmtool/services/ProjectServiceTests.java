package io.agile.ppmtool.services;

import io.agile.ppmtool.dto.ProjectDTO;
import io.agile.ppmtool.dto.ProjectDTOMapper;
import io.agile.ppmtool.models.Project;
import io.agile.ppmtool.repositories.ProjectRepository;
import io.agile.ppmtool.services.ProjectService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Unit testing | Project Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class ProjectServiceTests {

    @Mock
    ProjectRepository projectRepository;

    @Mock
    ProjectDTOMapper projectDTOMapper;

    @InjectMocks
    ProjectService projectService;

    Project project;

    Project updateProject;

    ProjectDTO projectDTO;

    List<Project> projects;

    @BeforeEach
    public void setup() {
        project = new Project(1L, "Test Project", "PO-01", "Project test description", new Date(), null, new Date(), null);
        projectDTO = new ProjectDTO(1L, "Test Project updated", "Project test description updated");
        updateProject = new Project(1L, "Test Project updated", "PO-01", "Project test description updated", new Date(), null, new Date(), null);
        projects = Arrays.asList(project,  new Project(2L, "Test Project2", "PO-02", "Project test description2", new Date(), null, new Date(), null));
    }

    @Test
    @DisplayName("Unit Test | Create Project")
    @Order(1)
    public void test_createProject() {

        when(projectRepository.save(project)).thenReturn(project);
        Project returnedProject = projectService.createProject(project);

        assertEquals(1L, returnedProject.getId());
        assertEquals("Test Project", returnedProject.getProjectName());
        assertEquals("PO-01", returnedProject.getProjectIdentifier());
    }

    @Test
    @DisplayName("Unit Test | Get Project By Identifier")
    @Order(2)
    public void test_getProjectByIdentifier() {

        when(projectRepository.getProjectByProjectIdentifier(project.getProjectIdentifier())).thenReturn(java.util.Optional.ofNullable(project));
        Project returnedProject = projectService.getProjectByIdentifier(project.getProjectIdentifier());

        assertEquals("PO-01", returnedProject.getProjectIdentifier());
    }

    @Test
    @DisplayName("Unit Test | Get All Projects")
    @Order(3)
    public void test_getAllProjects() {

        when(projectRepository.findAll()).thenReturn(projects);
        List<Project> returnedProjects = projectService.getAllProjects();

        assertEquals(2, returnedProjects.size());
        assertEquals("PO-02", projects.get(1).getProjectIdentifier());
    }

    @Test
    @DisplayName("Unit Test | Update Project")
    @Order(4)
    public void test_updateProject() {

        when(projectRepository.findById(projectDTO.getId())).thenReturn(java.util.Optional.ofNullable(project));
        when(projectDTOMapper.mapDtoToEntity(projectDTO, project)).thenReturn(updateProject);
        when(projectRepository.save(updateProject)).thenReturn(updateProject);
        Project updatedProject = projectService.updateProjectById(1L, projectDTO);

        assertEquals(1L, updatedProject.getId());
        assertEquals("PO-01", updatedProject.getProjectIdentifier());
        assertEquals("Test Project updated", updatedProject.getProjectName());
        assertEquals("Project test description updated", updatedProject.getDescription());
    }

    @Test
    @DisplayName("Unit Test | Delete Project By Id")
    @Order(5)
    public void test_deleteProjectById() {

        when(projectRepository.findById(project.getId())).thenReturn(java.util.Optional.ofNullable(project));
        doNothing().when(projectRepository).deleteById(project.getId());
        projectService.deleteProjectById(project.getId());

        verify(projectRepository, times(1)).deleteById(project.getId());
    }

    @Test
    @DisplayName("Unit Test | Delete Project By Identifier")
    @Order(6)
    public void test_deleteProjectByIdentifier() {

        when(projectRepository.getProjectByProjectIdentifier(project.getProjectIdentifier())).thenReturn(java.util.Optional.ofNullable(project));
        doNothing().when(projectRepository).delete(project);
        projectService.deleteProjectByIdentifier(project.getProjectIdentifier());

        verify(projectRepository, times(1)).delete(project);
    }
}
