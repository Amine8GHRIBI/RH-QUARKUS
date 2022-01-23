package activity;

import java.util.ArrayList;
import java.util.List;

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
import project.ProjectRepository;



@ApplicationScoped
public class ActivityRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityRepository.class);

    private final PgPool client;

    @Inject
    public ActivityRepository(PgPool _client) {
        this.client = _client;
    }
	
	
	public  Multi<Activity> findAll() {
		return this.client.query(
				"SELECT Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID FROM activities ORDER BY StartTime ASC;")
				.execute().onItem().transformToMulti(set -> Multi.createFrom().iterable(set)).onItem()
				.transform(this::rowToActivity);
	}

	public  Uni<Activity> findById( String Id) {
		return client.preparedQuery("SELECT * FROM activities WHERE id = $1;").execute(Tuple.of(Id)).onItem()
				.transform(m -> m.iterator().hasNext() ? rowToActivity(m.iterator().next()) : null);
	}


	
	public  Multi<Activity> getProjectActivities( String id) {
		return this.client.preparedQuery("select * from activities where ProjectId = " + id + " order by StartTime ASC;")
				.execute().onItem().transformToMulti(set -> Multi.createFrom().iterable(set)).onItem()
				.transform(this::rowToActivity);
	}
	
	public  Multi<Activity> getEmployeeActivities(String id) {
		return this.client.preparedQuery("select * from activities where TaskId = " + id + " order by StartTime ASC;")
				.execute().onItem().transformToMulti(set -> Multi.createFrom().iterable(set)).onItem()
				.transform(this::rowToActivity);
	}

	public Uni<String> save( String Subject, String Location, String StartTime, String EndTime,
			Boolean IsAllDay, String StartTimezone, String EndTimezone, String ProjectId, String TaskId,
			String Description, String RecurrenceRule, String Id, String RecurrenceException, String RecurrenceID) {
		List<Object> columns = new ArrayList<>();
		columns.add(Subject);
		columns.add(Location);
		columns.add(StartTime);
		columns.add(EndTime);
		columns.add(IsAllDay);
		columns.add(StartTimezone);
		columns.add(EndTimezone);
		columns.add(ProjectId);
		columns.add(TaskId);
		columns.add(Description);
		columns.add(RecurrenceRule);
		columns.add(Id);
		columns.add(RecurrenceException);
		columns.add(RecurrenceID);
		return client.preparedQuery(
				"INSERT INTO activities (Subject,Location,StartTime,EndTime,IsAllDay,StartTimezone,EndTimezone,ProjectId,TaskId,Description,RecurrenceRule,Id,RecurrenceException,RecurrenceID) VALUES ($1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12,$13,$14) RETURNING Id")
				.execute(Tuple.tuple(columns)).onItem().transform(m -> m.iterator().next().getString("id"));
	}
	
	public  Uni<Integer> update( String d1, String d2, String d3, String d4, Boolean d5, String d6, String d7, String d8, String d9, String d10, String d11, String id, String d12, String d13) {
		
		List<Object> columns = new ArrayList<>();
		columns.add(d1);
		columns.add(d2);
		columns.add(d3);
		columns.add(d4);
		columns.add(d5);
		columns.add(d6);
		columns.add(d7);
		columns.add(d8);
		columns.add(d9);
		columns.add(d10);
		columns.add(d11);
		columns.add(id);
		columns.add(d12);
		columns.add(d13);
		
        return this.client
                .preparedQuery("UPDATE activities SET Subject=$1,Location=$2,StartTime=$3,EndTime=$4,IsAllDay=$5,StartTimezone=$6,EndTimezone=$7,ProjectId=$8,TaskId=$9,Description=$10,RecurrenceRule=$11,Id=$12,RecurrenceException=$13,RecurrenceID=$14 WHERE id=$12") 
                .execute(Tuple.tuple(columns))
                .map(RowSet::rowCount);
    }
/*
	public  Uni<Object> delete( String id) {
		return client.preparedQuery("DELETE FROM activities WHERE id = $1").execute(Tuple.of(id)).onItem()
				.transform(m -> m.rowCount() == 1);
	}
	*/
	
	 public Uni<Integer> delete(String id) {
	        return client.preparedQuery("DELETE FROM activities WHERE id = $1")
	                .execute(Tuple.of(id))
	                .map(RowSet::rowCount);
	    }
	
	
	
	private Activity rowToActivity(Row row) {
		 
		return new Activity(row.getString("subject"), row.getString("location"), row.getString("starttime"),
				row.getString("endtime"), row.getBoolean("isallday"), row.getString("starttimezone"),
				row.getString("endtimezone"), row.getString("projectid"), row.getString("taskid"),
				row.getString("description"), row.getString("recurrencerule"), row.getString("id"),
				row.getString("recurrenceexception"), row.getString("recurrenceid"));
	}

}
