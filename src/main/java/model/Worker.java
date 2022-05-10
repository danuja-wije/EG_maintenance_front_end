package model;

public class Worker {
	private int taskID;

	private String workerID;
	
	private String role;

	public int getTaskID() {
		return taskID;
	}

	public String getWorkerID() {
		return workerID;
	}
	
	public String getRole() {
		return role;
	}


	public Worker(int taskID, String workerID,String role) {
		super();
		this.taskID = taskID;
		this.workerID = workerID;
		this.role = role;
	}
	
}
