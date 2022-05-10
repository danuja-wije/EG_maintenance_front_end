package model;

public class Employee {
	private String empID;
	private String name;
	private String email;
	private String empType;
	
	public Employee(String empID, String name, String email, String empType) {
		super();
		this.empID = empID;
		this.name = name;
		this.email = email;
		this.empType = empType;
	}

	public String getEmpID() {
		return empID;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getEmpType() {
		return empType;
	}
	
	
}
