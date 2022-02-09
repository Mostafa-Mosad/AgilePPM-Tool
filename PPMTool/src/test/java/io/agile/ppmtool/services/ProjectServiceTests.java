package io.agile.ppmtool.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.agile.ppmtool.dto.ProjectDTO;
import io.agile.ppmtool.dto.ProjectDTOMapper;
import io.agile.ppmtool.models.Project;
import io.agile.ppmtool.repositories.ProjectRepository;
import io.agile.ppmtool.services.ProjectService;
import io.agile.ppmtool.utils.MockReadUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
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

    ProjectDTO projectDTO;

    List<Project> projects;

    @BeforeEach
    public void setup() throws IOException {
        project = (Project) MockReadUtils.readObjectFromJsonFile("classpath:services/project-service/project.json", Project.class);
        projectDTO = (ProjectDTO) MockReadUtils.readObjectFromJsonFile("classpath:dto/project-dto-request.json", ProjectDTO.class);
        projects = MockReadUtils.readListFromJsonFile("classpath:services/project-service/project-list.json", Project.class);
    }

    @Test
    @DisplayName("Unit Test | Create Project")
    @Order(1)
    public void test_createProject() {

        when(projectRepository.save(project)).thenReturn(project);
        Project returnedProject = projectService.createProject(project);

        assertEquals(1L, returnedProject.getId());
        assertEquals("Project Name Test updated", returnedProject.getProjectName());
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
        assertEquals("po-02", projects.get(1).getProjectIdentifier());
    }

    @Test
    @DisplayName("Unit Test | Update Project")
    @Order(4)
    public void test_updateProject() {

        when(projectRepository.findById(projectDTO.getId())).thenReturn(java.util.Optional.ofNullable(project));
        when(projectDTOMapper.mapDtoToEntity(projectDTO, project)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        Project updatedProject = projectService.updateProjectById(1L, projectDTO);

        assertEquals(1L, updatedProject.getId());
        assertEquals("PO-01", updatedProject.getProjectIdentifier());
        assertEquals("Project Name Test updated", updatedProject.getProjectName());
        assertEquals("Project Test description", updatedProject.getDescription());
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
