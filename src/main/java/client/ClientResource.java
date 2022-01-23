package client;

import static javax.ws.rs.core.Response.status;

import java.net.URI;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
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

import employee.EmployeeRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import project.ProjectResource;


@Path("clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped

public class ClientResource {
	
	private final static Logger LOGGER = Logger.getLogger(ClientResource.class.getName());
	
	 private final ClientRepository clients ;
	 
	 @Inject
	    public ClientResource(ClientRepository clients) {
	        this.clients = clients;
	    }

  @PostConstruct
  void config() {
      
  }
	
	 @GET
	    public Multi<Client> getAll() {
	        return this.clients.findAll();
	    }
	    
	    @POST
	    public Uni<Response> create(Client clientt) {
	        String uniqueID = UUID.randomUUID().toString();
	        return this.clients.save(uniqueID,clientt.getFirst_name(),clientt.getLast_name(),clientt.getGrade(),clientt.getCompany())
	                .onItem()
	                .transform(id -> URI.create("clients/" + id))
	                .onItem()    
	                .transform(uri -> Response.created(uri).build());
	    }
	    
	    @DELETE
	    @Path("{id}")
	    public Uni<Response> delete(@PathParam("id") String id) {
	    	 return this.clients.delete( id)
	                 .map(deleted -> deleted > 0 ? Status.NO_CONTENT : Status.NOT_FOUND)
	                 .map(status -> status(status).build());
	    }
	    
	    
	    private void initdb() {
	        /*client.query("DROP TABLE IF EXISTS clients").execute()
	                .flatMap(m-> client.query("CREATE TABLE clients (id character varying(50) PRIMARY KEY NOT NULL, " +
	                        "first_name character varying(50), "+
	                        "last_name character varying(50), "+
	                        "grade character varying(50), "+
	                        "company character varying(50) )").execute())
	                .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('1','John','Smith','CEO','vPro Infoweb LLC')").execute())
	                .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('2','Gary','Camara','Marketing Head','Fly2 Infotech')").execute())
	                .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('3','Hossein','Shams','CEO','BT Technology')").execute())
	                .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('4','Maryam','Amiri','CEO','Core Infoweb Pvt')").execute())
	                .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('5','Abdellaziz','Hammami','Manager','Talan')").execute())
	                .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('6','Sundar','Pichai','PDG','Google')").execute())
	                .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('7','Steve','Jobs','Fondateur','Apple')").execute())
	                .flatMap(m -> client.query("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES('8','Mark','Zuckerberg','Fondateur-PDG','Facebook')").execute())
	                .await()
	                .indefinitely();*/
	    }
		

}
