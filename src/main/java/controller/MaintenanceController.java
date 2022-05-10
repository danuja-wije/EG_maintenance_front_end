
package controller;

//import packages
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import client.ClientManager;
import model.Interruption;
import service.MaintenanceService;
import service.MaintenencrServiceImpl;


//declare path
@Path("/")
public class MaintenanceController {

	//object creation

	MaintenanceService maintenanceService = new MaintenencrServiceImpl();
	ClientManager clientManager = new ClientManager();
	Gson gson = new Gson();

	//define HTTP verbs

	//	=====================================VIEW=========================================

	@GET	
	@Path("/Interruptions")
	@Produces(MediaType.APPLICATION_JSON) 

	public String allInterruptions(@HeaderParam("Authorization") String auth) {

		//input validation 
		if(auth == null) {
			return "User need authentication";
		}

		String currentUser = clientManager.getCurrentUser(auth);

		//validate current user Login
		if(currentUser.equals("login failed")) {
			String output = "login failed"; //failed message
			String jsonString = gson.toJson(output);
			return jsonString;
		}else {
			List<Interruption> list = maintenanceService.allInterruptions();
			String jsonString = gson.toJson(list); //convert list object to json object
			return jsonString;
		}

	}

	//	=====================================INSERT=========================================

	@POST
	@Path("/Interruptions")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)

	public String insertInterruption(@FormParam("intType") String intType,@FormParam("title") String title,@FormParam("description") String description,@FormParam("startDate") String startDate,@FormParam("endDate") String endDate,@FormParam("approval")String approval,@FormParam("custIDs") String custIDs,@HeaderParam("Authorization") String auth) {

		//input validation 
		if(auth == null) {
			return "User need authentication";
		}else if(intType == null || title == null || description == null || startDate == null || endDate == null || custIDs == null || approval == null) {
			return "input field cannot be empty";
		}
		String[] list_str = custIDs.split(";"); //split customer list string

		List<String>list = new ArrayList<String>();
		for (String string : list_str) {
			list.add(string);
		}
		String output = "";

		//validate current user login
		String currentUser = clientManager.getCurrentUser(auth);

		if(currentUser.equals("login failed")) {
			output = "login failed"; //failed message
		}
		else {
			Interruption interruption = new Interruption(intType, title, description, startDate, endDate, list, approval,currentUser);
			output = maintenanceService.insertInterruption(interruption);
		}

		return output;
	}

	//	=====================================UPDATE=========================================
	@PUT
	@Path("/Interruptions")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateInterruption(@FormParam("id") int id,@FormParam("intType") String intType,@FormParam("title") String title,@FormParam("description") String description,@FormParam("startDate") String startDate,@FormParam("endDate") String endDate,@FormParam("approval")String approval,@HeaderParam("Authorization") String auth) {
		//input validation 
		if(auth == null) {
			return "User need authentication";
		}else if(intType == null || title == null || description == null || startDate == null || endDate == null || approval == null) {
			return "input field cannot be empty";
		}

		//validate current user login
		String output = "";
		String currentUser = clientManager.getCurrentUser(auth);

		if(currentUser.equals("login failed")) {
			output = "login failed";
		}else {
			Interruption interruption = new Interruption(intType, title, description, startDate, endDate, null, approval,currentUser);
			interruption.setId(id);
			output = maintenanceService.updateInterruption(interruption);	
		}


		return output;
	}

	@PUT
	@Path("/Interruptions/effected")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateEffectedCustomer(@FormParam("id") int id,@FormParam("custIDs") String custIDs,@HeaderParam("Authorization") String auth) {
		//input validation 
		if(auth == null) {
			return "User need authentication";
		}else if( custIDs == null) {
			return "input field cannot be empty";
		}
		//validate current user login
		String output = "";
		String currentUser = clientManager.getCurrentUser(auth);

		if(currentUser.equals("login failed")) {
			output = "login failed";
		}else {
			String[] list = custIDs.split(";");
			output = maintenanceService.updateEffectedCustomer(id,list);
		}

		return output;
	}

	//	=====================================DELETE=========================================

	@DELETE
	@Path("/Interruptions/{id}")
	@Produces(MediaType.TEXT_PLAIN)

	public String deleteInterruption(@PathParam("id") int id,@HeaderParam("Authorization") String auth) {
		//input validation 
		if(auth == null) {
			return "User need authentication";
		}

		//validate current user login
		String output = "";
		String currentUser = clientManager.getCurrentUser(auth);

		if(currentUser.equals("login failed")) {
			output = "login failed";
		}else {
			output = maintenanceService.deleteInterruption(id);
		}

		return output;
	}


}
