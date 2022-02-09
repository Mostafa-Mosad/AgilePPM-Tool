package io.agile.ppmtool.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.agile.ppmtool.dto.ProjectDTO;
import io.agile.ppmtool.exceptions.ProjectNotFoundException;
import io.agile.ppmtool.models.Project;
import io.agile.ppmtool.services.ProjectService;
import io.agile.ppmtool.utils.MockReadUtils;
import org.junit.jupiter.api.*;

import org.mockito.internal.stubbing.answers.DoesNothing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Project Controller | Integration Test")
public class ProjectControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ProjectService projectService;

    private ObjectMapper mapper;

    private String baseRoute;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mapper = new ObjectMapper();
        baseRoute = "/api/project";
    }

    @Test
    @Order(1)
    @DisplayName("Test Create Project")
    public void createProject() throws Exception {

        Project project = (Project) MockReadUtils.readObjectFromJsonFile("classpath:services/project-service/project.json", Project.class);
        when(projectService.createProject(project)).thenReturn(project);

        ResultActions resultActions = mockMvc.perform(post(baseRoute + "/createproject")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(mapper.writeValueAsString(project))).andDo(print());

        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.projectName").value("Project Name Test"));
        resultActions.andExpect(jsonPath("$.projectIdentifier").value("PO-01"));
    }

    @Test
    @Order(2)
    @DisplayName("Test Get Project By Identifier | Success Scenario")
    public void getProjectByIdentifier_Success() throws Exception {

        Project project = (Project) MockReadUtils.readObjectFromJsonFile("classpath:services/project-service/project.json", Project.class);
        when(projectService.getProjectByIdentifier(project.getProjectIdentifier())).thenReturn(project);

        ResultActions resultActions = mockMvc.perform(get(baseRoute + "/getprojectbyidentifier/{projectIdentifier}", "PO-01"))
                .andDo(print());

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.projectIdentifier").value("PO-01"));
    }

    @Test
    @Order(3)
    @DisplayName("Test Get Project By Identifier | Not Found Scenario")
    public void getProjectByIdentifier_NotFound() throws Exception {

        when(projectService.getProjectByIdentifier("PO-03")).thenThrow(new ProjectNotFoundException("Project Not Found"));

        ResultActions resultActions = mockMvc.perform(get(baseRoute + "/getprojectbyidentifier/{projectIdentifier}", "PO-03"))
                .andDo(print());

        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    @DisplayName("Test Get All Projects")
    public void getAllProjects() throws Exception {

        List<Project> projects = MockReadUtils.readListFromJsonFile("classpath:services/project-service/project-list.json", Project.class);
        when(projectService.getAllProjects()).thenReturn(projects);

        ResultActions resultActions = mockMvc.perform(get(baseRoute + "/getallprojects")).andDo(print());

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.[0].projectIdentifier").value("PO-01"));
        resultActions.andExpect(jsonPath("$.[1].projectIdentifier").value("PO-02"));
    }

    @Test
    @Order(5)
    @DisplayName("Test Update Project | Success Scenario")
    public void updateProject_Success() throws Exception {

        Project project = (Project) MockReadUtils.readObjectFromJsonFile("classpath:services/project-service/project.json", Project.class);
        ProjectDTO projectDTO = (ProjectDTO) MockReadUtils.readObjectFromJsonFile("classpath:dto/project-dto-request.json", ProjectDTO.class);
        when(projectService.updateProjectById(projectDTO.getId(), projectDTO)).thenReturn(project);

        ResultActions resultActions = mockMvc.perform(put(baseRoute + "/updateproject/{projectId}", 1)).andDo(print());

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.projectName").value("Project Name Test"));
        resultActions.andExpect(jsonPath("$.description").value("Project Test description"));
    }

    @Test
    @Order(6)
    @DisplayName("Test Update Project | Not Found Scenario")
    public void updateProject_NotFound() throws Exception {

        Project project = (Project) MockReadUtils.readObjectFromJsonFile("classpath:services/project-service/project.json", Project.class);
        ProjectDTO projectDTO = (ProjectDTO) MockReadUtils.readObjectFromJsonFile("classpath:dto/project-dto-request.json", ProjectDTO.class);
        when(projectService.updateProjectById(project.getId(), projectDTO)).thenThrow(new ProjectNotFoundException("Project Not Found"));

        ResultActions resultActions = mockMvc.perform(put(baseRoute + "/updateproject/{projectId}", 1)).andDo(print());

        resultActions.andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    @DisplayName("Test Delete Project By Id")
    public void deleteProjectById() throws Exception {

        ResultActions resultActions = mockMvc.perform(delete(baseRoute + "/deleteprojectbyid/{projectId}", 1)).andDo(print());

        resultActions.andExpect(status().isOk());
    }

    @Test
    @Order(8)
    @DisplayName("Test Delete Project By Identifier")
    public void deleteProjectByIdentifier_NotFound() throws Exception {

        ResultActions resultActions = mockMvc.perform(delete(baseRoute + "/deleteprojectbyidentifier/{projectIdentifier}", "PO-03")).andDo(print());

        resultActions.andExpect(status().isOk());
    }
}
