package project;

 

import javax.persistence.Entity;

import employee.Employee;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

 
@Entity
public class Project {

 
	private String id;
    private String title;
    private String description;
    private String datedebut;
    private String datefin;
    private String client;

 

    
 

    public String getId() {
        return id;
    }

 

    public void setId(String id) {
        this.id = id;
    }

 

    public String getDescription() {
        return description;
    }

 

    public void setDescription(String description) {
        this.description = description;
    }

 

    public String getDatedebut() {
        return datedebut;
    }

 

    public void setDatedebut(String datedebut) {
        this.datedebut = datedebut;
    }

 

    public String getDatefin() {
        return datefin;
    }

 

    public void setDatefin(String datefin) {
        this.datefin = datefin;
    }

 

    public String getClient() {
        return client;
    }

 

    public void setClient(String client) {
        this.client = client;
    }

 

    public String getTitle() {
        return title;
    }

 

    public void setTitle(String title) {
        this.title = title;
    }

   
 
	public Project(String id,String title, String description, String datedebut, String datefin, String client) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.client = client;
	}



	public Project() {
		
	}






	



    
}