package affectation;

import employee.Employee;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

public class Affectation {

    private String employee_id;
    private String admin_id;
    private String project_id;
    private String debut_affectation;
    private String fin_affectation;

    public Affectation(String employee_id, String admin_id, String project_id,String debut_affectation,String fin_affectation) {
        this.employee_id = employee_id;
        this.admin_id = admin_id;
        this.project_id = project_id;
        this.debut_affectation=debut_affectation;
        this.fin_affectation=fin_affectation;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }
    public String getDebut_affectation() {
        return debut_affectation;
    }

    public void setDebut_affectation(String debut_affectation) {
        this.debut_affectation = debut_affectation;
    }

    public String getFin_affectation() {
        return fin_affectation;
    }
    
    public void setFin_affectation(String fin_affectation) {
        this.fin_affectation = fin_affectation;
    }
    
   
}