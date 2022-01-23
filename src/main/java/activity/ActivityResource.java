package activity;

import static javax.ws.rs.core.Response.status;

import java.net.URI;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import affectation.Affectation;
import affectation.AffectationRepository;
import affectation.AffectationResources;
import employee.Employee;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import project.Project;

@Path("Activities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActivityResource {

	  private final static Logger LOGGER = Logger.getLogger(ActivityResource.class.getName());

	    private final ActivityRepository activities;

	    @Inject
	    public ActivityResource(ActivityRepository activities) {
	        this.activities = activities;
	    }
	    

	    

	@GET
	public Multi<Activity> getAll() {
		return this.activities.findAll();
	}

	
	
	
	@GET
	@Path("{id}")
	public Uni<Response> get(@PathParam("id") String id) {
		return this.activities.findById( id).onItem().transform(
				Activity -> Activity != null ? Response.ok(Activity) : Response.status(Response.Status.NOT_FOUND))
				.onItem().transform(Response.ResponseBuilder::build);
	}
	
	
	@POST
	public Uni<Response> create(Activity activity) {
		String uniqueID = UUID.randomUUID().toString();
		return this.activities
				.save( activity.getSubject(), activity.getLocation(), activity.getStartTime(), activity.getEndTime(),
						activity.getIsAllDay(), activity.getStartTimezone(), activity.getEndTimezone(), activity.getProjectId(),
						activity.getTaskId(), activity.getDescription(), activity.getRecurrenceRule(), uniqueID,
						activity.getRecurrenceException(), activity.getRecurrenceID())
				.onItem().transform(id -> URI.create("/Activities/" + id)).onItem()
				.transform(uri -> Response.created(uri).build());
	}
	
	
	
	
	
	
	@DELETE
    @Path("{id}")
    public Uni<Response> delete(@PathParam("id") String id) {
    	 return this.activities.delete( id)
                 .map(deleted -> deleted > 0 ? Status.NO_CONTENT : Status.NOT_FOUND)
                 .map(status -> status(status).build());
    }
    

    
    
	
	
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> update(@PathParam("id") String id, Activity data) {
    	System.out.println(data.getSubject());
        return this.activities.update( data.getSubject(), data.getLocation(), data.getStartTime(), data.getEndTime(), data.getIsAllDay(), data.getStartTimezone(), data.getEndTimezone(), data.getProjectId(), data.getTaskId(), data.getDescription(), data.getRecurrenceRule(), id, data.getRecurrenceException(), data.getRecurrenceID())
        		.onItem()
                .transform(updated -> updated > 0 ? Response.Status.NO_CONTENT : Response.Status.NOT_FOUND)
                .onItem()
                .transform(status -> Response.status(status).build());
    }

	@GET
	@Path("Project/{id}")
	public Multi<Activity> getProjectActivities(@PathParam("id") String id) {
		return this.activities.getProjectActivities( id);
	}
	
	@GET
	@Path("Employee/{id}")
	public Multi<Activity> getEmployeeActivities(@PathParam("id") String id) {
		return this.activities.getEmployeeActivities( id);
	}
/*
	 @GET
	    @Path("Projects/{id}")
	    public Multi<Employee> getEmployees(@PathParam("id") String id) {
	        return this.affectations.getEmployees(id);
	    }
	    
	    @GET
	    @Path("Employee/{id}")
	    public Multi<Project> getProjects(@PathParam("id") String id) {
	        return this.getProjects(id);
	    }
	
	private void initdb() {
		// Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID
		 this.activities.query("DROP TABLE IF EXISTS Activities").execute() .flatMap(m ->
		 client.query("CREATE TABLE Activities (" +
		 "Subject character varying(50), "+
		 "Location character varying(50), "+
		 "StartTime character varying(50), " +
		 "EndTime character varying(50), " +
		 "IsAllDay boolean, "+
		 "StartTimezone character varying(50), "+
		 "EndTimezone character varying(50), "+
		 "ProjectId character varying(50), "+
		 "TaskId character varying(50), "+
		 "Description character varying(1000), "+
		 "RecurrenceRule character varying(50), "+
		 "Id character varying(50), "+
		 "RecurrenceException character varying(50), "+
		 "RecurrenceID character varying(50), "+
		 "PRIMARY KEY (Id))").execute()) 
		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Gestion Panier','Talan Charguia','2021-04-26T08:30:00.000Z','2021-04-26T11:00:00.000Z',false,null,null,'1','1','Ajout d un produit dans le panier et checkout',null,'1',null,null)" ).execute())
		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Meeting with Google Client','Talan Charguia','2021-04-26T11:30:00.000Z','2021-04-26T13:30:00.000Z',false,null,null,'1','1','Meeting with Google Manager Client',null,'2',null,null)" ).execute())
		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Daily meeting','Talan Charguia','2021-04-26T08:30:00.000Z','2021-04-26T08:45:00.000Z',false,null,null,'1','2','Meeting pour synchroniser leur activites, discuter des problemes rencontes et de faire un plan pour les prochaines 24 heures ',null,'3',null,null)" ).execute())
		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Data Analyse','Talan Charguia','2021-04-26T13:00:00.000Z','2021-04-26T17:00:00.000Z',false,null,null,'1','2','Determiner le pourcentage d avoir un cancer selon l alimentation et les pratiques quotidiennes',null,'4',null,null)" ).execute())
		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Develop messaging framework','BMW Showroom Sousse','2021-04-26T14:00:00.000Z','2021-04-26T16:00:00.000Z',false,null,null,'2','3','',null,'5',null,null)" ).execute())
		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Maintenance d un simulateur en ligne','Zitouna marsa','2021-04-26T08:00:00.000Z','2021-04-26T10:00:00.000Z',false,null,null,'2','3','',null,'6',null,null)" ).execute())
		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Secure customer references','BIAT Nabeul','2021-04-26T16:00:00.000Z','2021-04-26T18:00:00.000Z',false,null,null,'2','4','',null,'7',null,null)" ).execute())
		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Gestion d une plateforme de location de voiture','automobile rue les orangers Tunis','2021-04-26T09:30:00.000Z','2021-04-26T15:30:00.000Z',false,null,null,'2','4','',null,'8',null,null)" ).execute())
		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Integration of recruitment module','Marhaba Hotel','2021-04-26T15:00:00.000Z','2021-04-26T17:00:00.000Z',false,null,null,'3','5','',null,'9',null,null)" ).execute())
		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Testing d un logiciel de restauration','Talan Charguia','2021-04-26T10:00:00.000Z','2021-04-26T13:00:00.000Z',false,null,null,'3','5','',null,'10',null,null)" ).execute())
		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Designing platefome Orange tunisie','Orange','2021-04-26T15:00:00.000Z','2021-04-26T17:00:00.000Z',true,null,null,'3','6','',null,'11',null,null)" ).execute())
		 .flatMap(m -> client.query( "INSERT INTO Activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES('Integration of recruitment module','Marhaba Hotel','2021-04-26T09:00:00.000Z','2021-04-26T12:00:00.000Z',false,null,null,'3','2','As the only consumer-facing part of HR, the recruitment platform is the entry point to your organization for candidates and their information. Since most organizational data derives from the hiring process, it is crucial that it is clean and easily transferrable. Fortunately, when recruiting and HCM software are well-integrated, it allows for uninterrupted transfer of all data related to a person’s profile between platforms.',null,'12',null,null)" ).execute())
		 .await()
		 .indefinitely();
		 
	}
	*/
}
