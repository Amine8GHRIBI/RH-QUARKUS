package org.talan;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.restassured.RestAssured;
import io.smallrye.mutiny.Multi;
import project.Project;
import project.ProjectRepository;
import project.ProjectResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mvel2.ast.NewObjectNode.NewObjectArray;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@QuarkusTest
public class ProjectResourceTest {
/*	
	@Inject 
	@RestClient
	ProjectResource projectresource ;
	
	
	
	@Test
	public void getAll()
	{
		
		Multi<Project> descirption = projectresource.getAllProjects() ; 
	}
	
	
	*/
	
	
	/*
	@InjectMock ProjectRepository projectRepository;
	  @Inject ProjectResource projectResource;

	  private Project project;

	  @BeforeEach
	  void setUp() {
	    project = new Project();
	    project.setId("15");
	    project.setTitle("TEST");
	    project.setDescription("EST");
	    project.setDatedebut("12/12/2020");
	    project.setDatefin("01/07/2021");
	    project.setClient("Talan");
	   
	  }
	  
	  @Test
	  void getAll() {
	    Multi<Project> projects ; 
	    projects.toString();
	    
	   // movies.add(movie);
	    Mockito.when(projectRepository.findAll()).thenReturn(projects);
	    Multi<Project> response = projectResource.getAllProjects();
	    assertNotNull(response);
	  //  assertEquals(Response.Status.OK.getStatusCode(), response.;
	   // assertNotNull(response.getEntity());
	    Multi<Project> entity = (Multi<Project>) ((Response) response).getEntity();
	    */

	
	
	
	   
	  }


	   
	