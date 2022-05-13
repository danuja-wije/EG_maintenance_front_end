package model;

import java.util.List;

public class Interruption {
	
	
	private int id;
	private String inType;
	private String title;
	private String description;
	private String interruptionStartDate;
	private String interruptionEndDate;
	private List<String> efectedList;
	private String approval;
	private String timeStamp;
	private String handledBy;
	
	
	//constructor
	public Interruption(String inType, String title, String description, String interruptionStartDate,
			String interruptionEndDate, List<String> efectedList, String approval,String handledBy) {
		super();
		this.inType = inType;
		this.title = title;
		this.description = description;
		this.interruptionStartDate = interruptionStartDate;
		this.interruptionEndDate = interruptionEndDate;

		this.efectedList = efectedList;
		this.approval = approval;
		this.handledBy = handledBy;
	}

	
	//setters & getters
	public int getId() {
		return id;
	}

	public String getInType() {
		return inType;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getInterruptionStartDate() {
		return interruptionStartDate;
	}

	public String getInterruptionEndDate() {
		return interruptionEndDate;
	}

	public List<String> getEfectedList() {
		return efectedList;
	}

	public String getApproval() {
		return approval;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getHandledBy() {
		return handledBy;
	}


	@Override
	public String toString() {
		return "Interruption [inType=" + inType + ", title=" + title + ", description=" + description
				+ ", interruptionStartDate=" + interruptionStartDate + ", interruptionEndDate=" + interruptionEndDate
				+ ", efectedList=" + efectedList + ", approval=" + approval + ", handledBy=" + handledBy + "]";
	}

}
