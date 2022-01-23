package activity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import employee.Employee;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;
import project.Project;
public class Activity {
    
	private String Subject;
	private String Location;
	private String StartTime;
	private String EndTime;
	private Boolean IsAllDay;
	private String StartTimezone;
	private String EndTimezone;
	private String ProjectId;
	private String TaskId;
	private String Description;
	private String RecurrenceRule;
	private String Id;
	private String RecurrenceException;
	private String RecurrenceID;

	public Activity(String Subject, String Location, String StartTime, String EndTime, Boolean IsAllDay,
			String StartTimezone, String EndTimezone, String ProjectId, String TaskId, String Description,
			String RecurrenceRule, String Id, String RecurrenceException, String RecurrenceID) {
		this.Subject = Subject;
		this.Location = Location;
		this.StartTime = StartTime;
		this.EndTime = EndTime;
		this.IsAllDay = IsAllDay;
		this.StartTimezone = StartTimezone;
		this.EndTimezone = EndTimezone;
		this.ProjectId = ProjectId;
		this.TaskId = TaskId;
		this.Description = Description;
		this.RecurrenceRule = RecurrenceRule;
		this.Id = Id;
		this.RecurrenceException = RecurrenceException;
		this.RecurrenceID = RecurrenceID;
	}

	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	public Boolean getIsAllDay() {
		return IsAllDay;
	}

	public void setIsAllDay(Boolean isAllDay) {
		IsAllDay = isAllDay;
	}

	public String getStartTimezone() {
		return StartTimezone;
	}

	public void setStartTimezone(String startTimezone) {
		StartTimezone = startTimezone;
	}

	public String getEndTimezone() {
		return EndTimezone;
	}

	public void setEndTimezone(String endTimezone) {
		EndTimezone = endTimezone;
	}

	public String getProjectId() {
		return ProjectId;
	}

	public void setProjectId(String projectId) {
		ProjectId = projectId;
	}

	public String getTaskId() {
		return TaskId;
	}

	public void setTaskId(String taskId) {
		TaskId = taskId;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getRecurrenceRule() {
		return RecurrenceRule;
	}

	public void setRecurrenceRule(String recurrenceRule) {
		RecurrenceRule = recurrenceRule;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getRecurrenceException() {
		return RecurrenceException;
	}

	public void setRecurrenceException(String recurrenceException) {
		RecurrenceException = recurrenceException;
	}

	public String getRecurrenceID() {
		return RecurrenceID;
	}

	public void setRecurrenceID(String recurrenceID) {
		RecurrenceID = recurrenceID;
	}

    
}