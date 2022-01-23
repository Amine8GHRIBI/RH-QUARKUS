package project;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import employee.Employee;

import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;

import java.net.URI;
import java.util.UUID;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

@Path("Projects")
@RegisterRestClient
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ProjectResource {

	 private final static Logger LOGGER = Logger.getLogger(ProjectResource.class.getName());
	
	 private final ProjectRepository projects ;
	 
	 @Inject
	    public ProjectResource(ProjectRepository projects) {
	        this.projects = projects;
	    }

    @PostConstruct
    void config() {
        
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Project> getAllProjects() {
        return this.projects.findAll();
    }
    
    
    @GET
    @Path("{id}")
    public Uni<Response> get(@PathParam("id") String id) {
        return this.projects.findById(id)
                .onItem()
                .transform(Project -> Project != null ? Response.ok(Project) : Response.status(Response.Status.NOT_FOUND))
                .onItem()
                .transform(Response.ResponseBuilder::build);
    }
   

    @POST
    public Uni<Response> create(Project project) {
        String uniqueID = UUID.randomUUID().toString();
        return this.projects.save(uniqueID,project.getTitle(),project.getDescription(),project.getDatedebut(),project.getDatefin(),project.getClient())
                .onItem()
                .transform(id -> URI.create("/Projects/" + id))
                .onItem()    
                .transform(uri -> Response.created(uri).build());
    }
    
    

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(@PathParam("id") String id) {
    	 return this.projects.delete( id)
                 .map(deleted -> deleted > 0 ? Status.NO_CONTENT : Status.NOT_FOUND)
                 .map(status -> status(status).build());
    }
    
    /*
    
    private void initdb() {
        /*client.query("DROP TABLE IF EXISTS projects").execute()
                .flatMap(m-> client.query("CREATE TABLE projects (id character varying(50) PRIMARY KEY NOT NULL, " +
                        "title character varying(50) NOT NULL, "+
                        "description character varying(200) NOT NULL, "+
                        "datedebut character varying(50) NOT NULL, "+
                        "datefin character varying(50) NOT NULL, "+
                        "client character varying(50) NOT NULL, "+
                        "FOREIGN KEY(client) REFERENCES clients(id))").execute())
                .flatMap(m -> client.query("INSERT INTO projects (id, title, description, datedebut, datefin,  client) VALUES('1','Projet de vente en ligne','un projet de developpement de site internet qui propose le positionnement, les catalogues de produits, la gestion des stocks, la vitrine, facture, paiement, communication','18/04/2021','29/5/2021','Aladin')").execute())
                .flatMap(m -> client.query("INSERT INTO projects (id, title, description, datedebut, datefin,  client) VALUES('2','Projet ERP','Un logiciel pour l entreprise contenet les modules comptabilite, RH, gestion des contacts, marketing, service client','01/06/2021','11/07/2022','Apple')").execute())
                .flatMap(m -> client.query("INSERT INTO projects (id, title, description, datedebut, datefin,  client) VALUES('3','SEO-friendly website','un site qui est optimise autant par sa forme que ses contenus pour avoir un referencement naturel sur les moteurs de recherche','03/27/2021','26/04/2022','Talan')").execute())
                .flatMap(m -> client.query("INSERT INTO projects (id, title, description, datedebut, datefin,  client) VALUES('4','JavaScript quiz game','un Quiz qui couvrit les fonctionnalites de Classement, Minuteur du quiz, score, Randomiser les questions, ecran Conclusions à la fin du quiz.','08/02/2021','11/04/2022','Sami Achour ISSAT')").execute())
                .flatMap(m -> client.query("INSERT INTO projects (id, title, description, datedebut, datefin,  client) VALUES('5','To-do list','Application web permettant de collaborer evec une equipe en ajoutant ses tâches d une maniere fluide, facile et rapide','12/09/2021','11/01/2022','Samsung')").execute())
                .await()
                .indefinitely();*/
    }

 

