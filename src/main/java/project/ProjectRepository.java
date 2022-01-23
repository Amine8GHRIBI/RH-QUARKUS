package project;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;
import java.util.stream.StreamSupport;

@ApplicationScoped
public class ProjectRepository  {
	 private static final Logger LOGGER = LoggerFactory.getLogger(ProjectRepository.class);

	    private final PgPool client;

	    @Inject
	    public ProjectRepository(PgPool _client) {
	        this.client = _client;
	    }

	 
	    
	    public Multi<Project> findAll() {
	        return this.client
	                .query("SELECT projects.id, projects.title, projects.description, projects.datedebut, projects.datefin, clients.company as client FROM projects,clients where projects.client=clients.id ORDER BY projects.id ASC")
	                .execute()
	                .onItem().produceMulti(
	                        rs -> Multi.createFrom().items(() -> StreamSupport.stream(rs.spliterator(), false))
	                )
	                .map(this::rowToProject);

	    }
	    
	    
	    public Uni<Project> findById(String id) {
	        return this.client
	                .preparedQuery("SELECT projects.id, projects.title, projects.description, projects.datedebut, projects.datefin, clients.company as client FROM projects,clients where projects.client=clients.id AND projects.id = $1")
	                .execute(Tuple.of(id))
	                .map(RowSet::iterator)
	                // .map(it -> it.hasNext() ? rowToPost(it.next()) : null);
	                .flatMap(it -> it.hasNext() ? Uni.createFrom().item(rowToProject(it.next())) : Uni.createFrom().failure(ProjectNotFoundException::new));
	    }
	   
	    
	    public Uni<String> save(String id, String title, String description, String datedebut, String datefin, String clientt) {
	        return client
	                .preparedQuery("INSERT INTO projects (id,title,description,datedebut,datefin,client) VALUES ($1,$2,$3,$4,$5,(select id from clients where company=$6)) RETURNING id")
	                .execute(Tuple.of(id,title,description,datedebut,datefin,clientt))
	                .onItem()
	                .transform(m -> m.iterator().next().getString("id"));
	    }

	    
	    public Uni<Integer> delete(String id) {
	        return client.preparedQuery("DELETE FROM projects WHERE id = $1")
	                .execute(Tuple.of(id))
	                .map(RowSet::rowCount);
	    }

	   private  Project rowToProject(Row row) {
	        return new Project(row.getString("id"), row.getString("title"), row.getString("description"), row.getString("datedebut"),row.getString("datefin"), row.getString("client"));
	    }
	   
	   
}




