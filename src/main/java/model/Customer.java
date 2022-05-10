package model;

public class Customer {
	private String cutomerID;
	private String name;
	private String address;
	
	public Customer(String cutomerID, String name, String address) {
		super();
		this.cutomerID = cutomerID;
		this.name = name;
		this.address = address;
	}
	public String getCutomerID() {
		return cutomerID;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	
	
}
