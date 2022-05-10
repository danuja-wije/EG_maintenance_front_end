package model;
//import models
import model.Worker;

import java.sql.*;
import java.util.List;

public class Task {

	private int taskID;
	private String title;
	private String desc;
	private String customerID;
	private List<Worker> worker;
	private String handleBy;
	private String create;
	private String lastUpdate;
	private String message;

	public Task(int taskID, String title, String desc, String customerID, List<Worker>worker, String handleBy,
			String create, String lastUpdate) {
		super();
		this.taskID = taskID;
		this.title = title;
		this.desc = desc;
		this.customerID = customerID;
		this.worker = worker;
		this.handleBy = handleBy;
		this.create = create;
		this.lastUpdate = lastUpdate;
	}

	public Task() {
		super();
		this.taskID = -1;
		this.title = null;
		this.desc = null;
		this.customerID = null;
		this.worker = null;
		this.handleBy = null;
		this.create = null;
		this.lastUpdate = null;
	}

	public int getTaskID() {
		return taskID;
	}

	public String getTitle() {
		return title;
	}

	public String getDesc() {
		return desc;
	}

	public String getCustomerID() {
		return customerID;
	}

	public List<Worker> getWorker() {
		return worker;
	}

	public String getHandleBy() {
		return handleBy;
	}

	public String getCreate() {
		return create;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
