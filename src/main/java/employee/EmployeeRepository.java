package employee;

import java.util.stream.StreamSupport;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import project.Project;
import project.ProjectNotFoundException;

@ApplicationScoped
public class EmployeeRepository {
	 private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRepository.class);

	    private final PgPool client;

	    @Inject
	    public EmployeeRepository(PgPool _client) {
	        this.client = _client;
	    }
	    
	    public Multi<Employee> findAll() {
	        return this.client
	                .query("SELECT employee_id,first_name,last_name,email FROM employees ORDER BY employee_id ASC")
	                .execute()
	                .onItem().produceMulti(
	                        rs -> Multi.createFrom().items(() -> StreamSupport.stream(rs.spliterator(), false))
	                )
	                .map(this::rowToEmployee);

	    }
	    public Uni<Employee> findById(String  employee_id) {
	        return this.client
	                .preparedQuery("SELECT * FROM employees WHERE employee_id = $1")
	                .execute(Tuple.of(employee_id))
	                .map(RowSet::iterator)
	                // .map(it -> it.hasNext() ? rowToPost(it.next()) : null);
	                .flatMap(it -> it.hasNext() ? Uni.createFrom().item(rowToEmployee(it.next())) : Uni.createFrom().failure(ProjectNotFoundException::new));
	    }
	   
	    
	    
	    

	    
	    public Uni<Integer> delete(String employee_id) {
	        return client.preparedQuery("DELETE FROM employees WHERE employee_id = $1")
	                .execute(Tuple.of(employee_id))
	                .map(RowSet::rowCount);
	    }
	   
	    private  Employee rowToEmployee(Row row) {
	    	return new Employee(row.getString("employee_id"), row.getString("first_name"), row.getString("last_name"),  row.getString("email"));	    }
	   
	    
	    
	    
	    public Uni<String> save(String employee_id, String first_name, String last_name,String email) {
	        
	        return client
	                .preparedQuery("INSERT INTO employees (employee_id,first_name,last_name,email) VALUES ($1,$2,$3,$4) RETURNING employee_id")
	                .execute(Tuple.of(employee_id,first_name,last_name,email))
	                .onItem()
	                .transform(m -> m.iterator().next().getString("employee_id"));
	    }
	    
	    public  Multi<Project> getProjects( String id) {
	        return this.client.preparedQuery("select p.id, p.title, p.description, p.datedebut, p.datefin,  p.client from affectation a, projects p, employees e where a.project_id=p.id and a.employee_id=e.employee_id and e.employee_id = "+id+" order by p.title ASC;")
	                .execute().onItem().transformToMulti(set -> Multi.createFrom().iterable(set)).onItem()
	                .transform(this::form);
	    }
	   
	    private  Project form(Row row) {
	        return new Project(row.getString("id"), row.getString("title"), row.getString("description"), row.getString("datedebut"),row.getString("datefin"), row.getString("client"));
	    }

}
