package client;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import employee.EmployeeRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import project.Project;

@ApplicationScoped
public class ClientRepository {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(ClientRepository.class);

	    private final PgPool client;

	    @Inject
	    public ClientRepository(PgPool _client) {
	        this.client = _client;
	    }
	
	
	public  Multi<Client> findAll() {
        return this.client
                .query("SELECT id,first_name,last_name,grade,company FROM clients ORDER BY id ASC")
                .execute().onItem().transformToMulti(set -> Multi.createFrom().iterable(set)).onItem()
                .transform(this::rowToClient);
    }
	
	public Uni<String> save(String id,String first_name,String last_name,String grade,String company) {    
        return client
                .preparedQuery("INSERT INTO clients (id,first_name,last_name,grade,company) VALUES ($1,$2,$3,$4,$5) RETURNING id")
                .execute(Tuple.of(id,first_name,last_name,grade,company))
                .onItem()
                .transform(m -> m.iterator().next().getString("id"));
    }
	
	
	 public Uni<Integer> delete(String id) {
	        return client.preparedQuery("DELETE FROM clients WHERE id = $1")
	                .execute(Tuple.of(id))
	                .map(RowSet::rowCount);
	    }
	
	
	
    private Client rowToClient(Row row) {
        return new Client(row.getString("id"), row.getString("first_name"), row.getString("last_name"),  row.getString("grade"),  row.getString("company"));
    }
   
}
