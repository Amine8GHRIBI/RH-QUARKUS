package employee;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import project.Project;
import project.ProjectRepository;
import project.ProjectResource;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static javax.ws.rs.core.Response.status;

import java.net.URI;
import java.util.UUID;
import java.util.logging.Logger;


@Path("all-employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class EmployeeResource {

	private final static Logger LOGGER = Logger.getLogger(EmployeeResource.class.getName());
	
	 private final EmployeeRepository employees ;
	 
	 @Inject
	    public EmployeeResource(EmployeeRepository employees) {
	        this.employees = employees;
	    }

   @PostConstruct
   void config() {
       
   }
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Multi<Employee> getAllEmployees() {
       return this.employees.findAll();
   }
   
  
   @GET
   @Path("{employee_id}")
   public Uni<Response> get(@PathParam("employee_id") String employee_id) {
       return this.employees.findById(employee_id)
               .onItem()
               .transform(Employee -> Employee != null ? Response.ok(Employee) : Response.status(Response.Status.NOT_FOUND))
               .onItem()
               .transform(Response.ResponseBuilder::build);
   }
   
   /*
   @GET
   @Path("employee/{id}")
   public Multi<Project> getProjects(@PathParam("id") String id) {
       return this.getProjects( id);
   }
   */
   @GET
   @Path("employee/{id}")
   public Multi<Project> getProjects(@PathParam("id") String id) {
       return this.employees.getProjects(id);
   }
   
   @POST
   public Uni<Response> create(Employee employee) {
       String uniqueID = UUID.randomUUID().toString();
       return this.employees.save(uniqueID,employee.getFirst_name(),employee.getLast_name(),employee.getEmail())
               .onItem()
               .transform(id -> URI.create("/all-employees/" + id))
               .onItem()    
               .transform(uri -> Response.created(uri).build());
   }
   
   @DELETE
   @Path("{employee_id}")
   public Uni<Response> delete(@PathParam("employee_id") String employee_id) {
   	 return this.employees.delete( employee_id)
                .map(deleted -> deleted > 0 ? Status.NO_CONTENT : Status.NOT_FOUND)
                .map(status -> status(status).build());
   }

  

   
    /*
  
    
    private void initdb() {
        /*client.query("DROP TABLE IF EXISTS employees").execute()
                .flatMap(m-> client.query("CREATE TABLE employees (employee_id character varying(50) PRIMARY KEY NOT NULL, " +
                        "first_name character varying(50), "+
                        "last_name character varying(50), "+
                        "email character varying(50) )").execute())
                .flatMap(m -> client.query("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('1','Haythem','Bahri','Bahri@gmail.com')").execute())
                .flatMap(m -> client.query("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('2','Ahmed','Kazdar','Ahmed@gmail.com')").execute())
                .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('3','Adam','Kossemtini','Adam@gmail.com')").execute())
                .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('4','Anas','Abdelkefi','abdelkefianas@gmail.com')").execute())
                .flatMap(m -> client.query ("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES('5','Malek','Slama','Malek@gmail.com')").execute())
                .await()
                .indefinitely();*/
    }