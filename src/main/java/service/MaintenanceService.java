package service;

import java.util.List;

import model.Interruption;

public interface MaintenanceService {
	public String insertInterruption(Interruption interruption);
	public List<Interruption> allInterruptions();
	public String updateInterruption(Interruption interruption);
	public String updateEffectedCustomer(int interruptionID,String[] list);
	public String deleteInterruption(int id);
}
