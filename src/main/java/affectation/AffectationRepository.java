package affectation;

import java.util.stream.StreamSupport;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import employee.Employee;
import employee.EmployeeRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import project.Project;


@ApplicationScoped
public class AffectationRepository {
	 private static final Logger LOGGER = LoggerFactory.getLogger(AffectationRepository.class) ; 
	 
	 


	    private final PgPool client;

	    @Inject
	    public AffectationRepository(PgPool _client) {
	        this.client = _client;
	    }
	    
	    
	    
	    
	/*
	 public static Multi<Affectation> findAll(PgPool client) {
	        //"SELECT * FROM affectation aff, projects p, employees e, employees ad where p.id=aff.project_id and e.employee_id=aff.employee_id and ad.employee_id=aff.employee_id ORDER BY id DESC"
	        return client
	                .query("SELECT * FROM affectation ORDER BY employee_id ASC")
	                .execute().onItem().transformToMulti(set -> Multi.createFrom().iterable(set)).onItem()
	                .transform(Affectation::from);
	    }
	 
	 */
	 public Multi<Affectation> findAll() {
	        return this.client
	                .query("SELECT * FROM affectation ORDER BY employee_id ASC")
	                .execute()
	                .onItem().produceMulti(
	                        rs -> Multi.createFrom().items(() -> StreamSupport.stream(rs.spliterator(), false))
	                )
	                .map(this::rowToAffectation);

	    }
	 
	 
	 
	 /*
	 
	    
	    public Uni<Affectation> save(PgPool client,String employee_id, String admin_id, String project_id,String debut_affectation,String fin_affectation) {
	        return client
	                .preparedQuery("INSERT INTO affectation (employee_id,admin_id,project_id,debut_affectation,fin_affectation) VALUES ($1,$2,$3,$4,$5)")
	                .execute(Tuple.of(employee_id,admin_id,project_id,debut_affectation,fin_affectation))
	                .onItem()
	                .transform(m -> m.iterator().hasNext() ? rowToAffectation(m.iterator().next()) : null);
	    }
	    */
 public Uni<Affectation> save(String employee_id, String admin_id, String project_id,String debut_affectation,String fin_affectation) {
	        
	        return client
	                .preparedQuery("INSERT INTO affectation (employee_id,admin_id,project_id,debut_affectation,fin_affectation) VALUES ($1,$2,$3,$4,$5)")
	                .execute(Tuple.of(employee_id,admin_id,project_id,debut_affectation,fin_affectation))
	                .onItem()
	                .transform(m -> m.iterator().hasNext() ? rowToAffectation(m.iterator().next()) : null);
	    }
	    
	    /*public Uni<String> save(PgPool client,String employee_id, String admin_id, String project_id) {
	        return client
	                .preparedQuery("INSERT INTO affectation (employee_id,admin_id,project_id) VALUES ($1,$2,$3)")
	                .execute(Tuple.of(employee_id,admin_id,project_id))
	                .onItem()
	                .transform(m -> m.iterator().next().getString("id"));
	    }
	*/
	    public  Uni<Boolean> delete( String employee_id, String admin_id, String project_id ) {
	        return client.preparedQuery("DELETE FROM affectation where affectation.employee_id=$1 and affectation.admin_id=$2 and affectation.project_id=$3;").execute(Tuple.of(employee_id,admin_id,project_id)).onItem()
	                .transform(m -> m.rowCount() == 1);
	    }
	    
	    
	    public  Multi<Employee> getEmployees( String id) {
	        return this.client.preparedQuery("select e.employee_id,e.first_name,e.last_name,e.email from affectation a,projects p,employees e where a.project_id=p.id and a.employee_id=e.employee_id and p.id = "+id+" order by e.first_name ASC;")
	                .execute().onItem().transformToMulti(set -> Multi.createFrom().iterable(set)).onItem()
	                .transform(this::form1);
	    }
	    

	    public  Multi<Project> getProjects( String id) {
	        return this.client.preparedQuery("select p.id, p.title, p.description, p.datedebut, p.datefin,  p.client from affectation a, projects p, employees e where a.project_id=p.id and a.employee_id=e.employee_id and e.employee_id = "+id+" order by p.title ASC;")
	                .execute().onItem().transformToMulti(set -> Multi.createFrom().iterable(set)).onItem()
	                .transform(this::form2);
	    }

	    public  Multi<Project> getManagerProjects( String id) {
	        return this.client.preparedQuery("SELECT projects.id, projects.title, projects.description, projects.datedebut, projects.datefin, clients.company as client FROM projects,clients where projects.client=clients.id ORDER BY projects.id ASC")
	                .execute().onItem().transformToMulti(set -> Multi.createFrom().iterable(set)).onItem()
	                .transform(this::form2);
	    }

	 /*
	    public  Multi<Project> getManagerProjects( String id) {​
	        return client.preparedQuery("select DISTINCT p.id, p.title, p.description, p.datedebut, p.datefin,  p.client from affectation a, projects p, employees e where a.project_id=p.id and a.admin_id=e.employee_id and a.admin_id = "+id+" order by p.title ASC;")
	                .execute().onItem().transformToMulti(set -> Multi.createFrom().iterable(set)).onItem()
	                .transform(this::form2);
	    }​
	    
	    
	   /* 
	    public  Multi<Employee> getEmployees( String id) {
	        return this.client.preparedQuery("select e.employee_id,e.first_name,e.last_name,e.email from affectation a,projects p,employees e where a.project_id=p.id and a.employee_id=e.employee_id and p.id = "+id+" order by e.first_name ASC;")
	                .execute().onItem().transformToMulti(set -> Multi.createFrom().iterable(set)).onItem()
	                .transform(this::form);
	    }
*/
	    private  Employee form1(Row row) {
	    	return new Employee(row.getString("employee_id"), row.getString("first_name"), row.getString("last_name"),  row.getString("email"));	    }
	  
	
	    private Affectation rowToAffectation(Row row) {
	        return new Affectation(row.getString("employee_id"), row.getString("admin_id"), row.getString("project_id"),row.getString("debut_affectation"),row.getString("fin_affectation"));
	    }
	    private  Project form2(Row row) {
	        return new Project(row.getString("id"), row.getString("title"), row.getString("description"), row.getString("datedebut"),row.getString("datefin"), row.getString("client"));
	    }
	   

}
