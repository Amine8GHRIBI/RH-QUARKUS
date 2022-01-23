package affectation;

 

import static javax.ws.rs.core.Response.status;

import java.net.URI;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import employee.Employee;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import project.Project;

 


@Path("Affectation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AffectationResources {

	  private final static Logger LOGGER = Logger.getLogger(AffectationResources.class.getName());

	    private final AffectationRepository affectations;

	    @Inject
	    public AffectationResources(AffectationRepository affectations) {
	        this.affectations = affectations;
	    }
	    

 

    @GET
    public Multi<Affectation> getAllAffectations() {
        return this.affectations.findAll();
    }

    

 

    @POST
    public Uni<Response> create(Affectation affectations) {
        return this.affectations.save(affectations.getEmployee_id(),affectations.getAdmin_id(),affectations.getProject_id(), affectations.getDebut_affectation(), affectations.getFin_affectation())
                .onItem()
                .transform(id -> URI.create("/Affectation/" + id))
                .onItem()    
                .transform(uri -> Response.created(uri).build());
    }
    


    @DELETE
    public Uni<Response> delete(Affectation affectations) {
        return this.affectations.delete( affectations.getEmployee_id(),affectations.getAdmin_id(),affectations.getProject_id())
                .onItem()
                .transform(deleted -> deleted ? Response.Status.NO_CONTENT : Response.Status.NOT_FOUND)
                .onItem()
                .transform(status -> Response.status(status).build());
    }
    
    @GET
    @Path("Projects/{id}")
    public Multi<Employee> getEmployees(@PathParam("id") String id) {
        return this.affectations.getEmployees(id);
    }
    
    @GET
    @Path("Employee/{id}")
    public Multi<Project> getProjects(@PathParam("id") String id) {
        return this.affectations.getProjects(id);
    }

    
    
    @GET
    @Path("Manager/{id}")
    public Multi<Project> getManagerProjects(@PathParam("id") String id) {
        return this.affectations.getManagerProjects( id);
    }
       
 
    
    /*
    private void initdb() {
        this.affectations.query("DROP TABLE IF EXISTS affectation").execute()
                .flatMap(m-> client.query("CREATE TABLE affectation ("+
                        "employee_id character varying(50) NOT NULL REFERENCES employees(employee_id), "+
                        "admin_id character varying(50) NOT NULL REFERENCES employees(employee_id), "+
                        "project_id character varying(50) NOT NULL REFERENCES projects(id), "+
                        "debut_affectation character varying(50) NOT NULL, "+
                        "fin_affectation character varying(50) NOT NULL, "+
                        "PRIMARY KEY (employee_id,admin_id,project_id))").execute())
                .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('2','1','5','21/04/2021','21/06/2021')").execute())
                .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('3','1','5','30/01/2021','01/01/2022')").execute())
                .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('5','1','2','21/04/2021','21/06/2021')").execute())
                .flatMap(m -> client.query("INSERT INTO affectation (employee_id, admin_id, project_id,debut_affectation,fin_affectation) VALUES('5','1','3','21/04/2021','21/06/2021')").execute())
                .await()
                .indefinitely();
    }
    */
}