package client;





public class Client {
	
	
	private String id;
    private String first_name;
    private String last_name;
    private String grade;
    private String company;
    
    public Client (String id,String first_name,String last_name,String grade,String company) {
        this.id = id;
        this.first_name=first_name;
        this.last_name=last_name;
        this.grade=grade;
        this.company=company;
    }
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
