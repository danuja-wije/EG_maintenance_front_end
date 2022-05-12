package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Interruption;

public class MaintenencrServiceImpl implements MaintenanceService {

	//connection details

	private static final String USERNAME= "root";
	private static final String PASSWORD = "";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/electro_db";
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

	//initialize variables

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	Statement statement = null;
	ResultSet resultSet = null;
	List<Interruption> list = null;
	Interruption interruption = null;
	private String query = "";

	//create connection
	private Connection connect() throws SQLException {
		if(connection != null && !connection.isClosed()) {
			return connection;
		}
		else {
			try {
				Class.forName(DRIVER);
				connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
				System.out.println("Successfully Connected Electro Main Database");


			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return connection;
	}

	//	=====================================INSERT LOGIC=========================================

	@Override
	public String insertInterruption(Interruption interruption) {
		// TODO Auto-generated method stub
		String output = "";

		try {
			connection = connect();

			if (connection == null) {
				output = "Error while conecting to the database for inserting";
				return output;
			}

			query = "INSERT INTO `interruption` (`intType`, `title`, `description`, `approval`, `interruptionStart`, `interruptionEnd`,`handledBy`) VALUES (?,?,?,?,?,?,?)";


			String query2= "INSERT INTO `efectedcustomer` (`interrruptionID`, `customerID`) VALUES (?, ?)";

			preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, interruption.getInType());
			preparedStatement.setString(2, interruption.getTitle());
			preparedStatement.setString(3, interruption.getDescription());
			preparedStatement.setString(4, interruption.getApproval());
			preparedStatement.setString(5, interruption.getInterruptionStartDate());
			preparedStatement.setString(6, interruption.getInterruptionEndDate());
			preparedStatement.setString(7, interruption.getHandledBy());
			preparedStatement.execute();

			int id = 0;
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					id = generatedKeys.getInt(1);
				}
				else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}
			preparedStatement = connection.prepareStatement(query2);


			for (String custID : interruption.getEfectedList()) {
				preparedStatement.setInt(1, id);
				preparedStatement.setString(2, custID);
				preparedStatement.execute();
			}

			connection.close();
			String table = allInterruptions();
			output = "{\"status\":\"success\", \"data\": \"" + table + "\"}";
			query = "";

		} catch (Exception e) {
			// TODO: handle exception
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the data.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	//	=====================================RETRIVE LOGIC=========================================

	@Override
	public String allInterruptions() {
		// TODO Auto-generated method stub
		list =new ArrayList<Interruption>();
		query = "SELECT * FROM `interruption`";
		String output = "";
		String table = "";
		try {
			connection = connect();
			if (connection == null) {
				System.out.println("Error while connecting to the database for view Data");
			}

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);


			ResultSet rs = null;

			while (resultSet.next()) {
				Statement st = connection.createStatement();
				List<String>efList = new ArrayList<String>();

				int id = resultSet.getInt("id");
				String inType = resultSet.getString("intType");
				String title = resultSet.getString("title");
				String description = resultSet.getString("description");
				String approval = resultSet.getString("approval");
				String timestamp = resultSet.getString("timestamp");
				String startDate = resultSet.getTimestamp("interruptionStart").toString();
				String endDate = resultSet.getTimestamp("interruptionEnd").toString();
				String handledBy = resultSet.getString("handledBy");
				//				
				String query2 = "SELECT * FROM `efectedcustomer` WHERE interrruptionID ='"+id+"'";

				rs = st.executeQuery(query2);

				while(rs.next()) {
					efList.add(rs.getString("customerID"));
				}


				interruption = new Interruption(inType, title, description, startDate,endDate ,efList,approval,handledBy);
				interruption.setId(id);
				interruption.setTimeStamp(timestamp);
				list.add(interruption);
			}
			
			table = "<table class='table'> <thead class='thead-dark'> <tr> <th scope='col'>Title</th>"+
			"<th scope='col'>Type</th>"+
			"<th scope='col'>Description</th>"+
			"<th scope='col'>StartDate</th>"+
			"<th scope='col'>EndDate</th>"+
			"<th scope='col'>EffectedList</th>"+
			"<th scope='col'>Approval</th>"+
			"<th scope='col'>HandledBy</th>"
			+ "<th scope='col'>Action</th>"
			+ "</tr><tbody>";
			
			for (Interruption interruption : list) {
				table += "<tr>"
						+ "<td><input type='hidden' name='hidItemIDUpdate' id='hideItemIDUpdate' value='"+interruption.getId()+"'>"+interruption.getTitle()+"</td>"
						+ "<td>"+interruption.getInType()+"</td>"
						+ "<td>"+interruption.getDescription()+"</td>"
						+ "<td>"+interruption.getInterruptionStartDate()+"</td>"
						+ "<td>"+interruption.getInterruptionEndDate()+"</td>"
						+ "<td>"+interruption.getEfectedList()+"</td>"
						+ "<td>"+interruption.getApproval()+"</td>"
						+ "<td>"+interruption.getHandledBy()+"</td>"
						+ "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-primary btn-sm' data-intid='"+interruption.getId()+"'>"
						+ "&nbsp&nbsp<input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger btn-sm' data-intid='"+interruption.getId()+"'></td>"
						+ "</tr>";
			}
			table +="</tbody></table>";
			output = "{\"status\":\"success\", \"data\": \"" + table + "\"}";
			connection.close();
			query = "";

		} catch (Exception e) {
			// TODO: handle exception
			output =  "{\"status\":\"error\", \"data\": \"Error while retriving the data.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	//	=====================================UPDATE LOGIC=========================================
	@Override
	public String updateInterruption(Interruption interruption) {
		// TODO Auto-generated method stub
		String output = "";

		try {
			connection = connect();

			if (connection == null) {
				output = "Error while conecting to the database for Updating interruption";
				return output;
			}

			query = "UPDATE `interruption` SET `intType` = ?, `title` = ?, `description` =?, `approval` = ?, `interruptionStart` = ?, `interruptionEnd` = ? WHERE `interruption`.`id` = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, interruption.getInType());
			preparedStatement.setString(2, interruption.getTitle());
			preparedStatement.setString(3, interruption.getDescription());
			preparedStatement.setString(4, interruption.getApproval());
			preparedStatement.setString(5, interruption.getInterruptionStartDate());
			preparedStatement.setString(6, interruption.getInterruptionEndDate());
			preparedStatement.setInt(7, interruption.getId());
			preparedStatement.execute();

			connection.close();

			output = "Updated Successfully";
			query = "";

		} catch (Exception e) {
			// TODO: handle exception
			output = "Error while inserting Updating";
			System.err.println(e.getMessage());
		}

		return output;
	}



	@Override
	public String updateEffectedCustomer(int interruptionID,String[] list) {

		// TODO Auto-generated method stub
		String output = "";

		try {
			connection = connect();

			if (connection == null) {
				output = "Error while conecting to the database for Updating interruption";
				return output;
			}

			query = "DELETE FROM efectedcustomer WHERE `efectedcustomer`.`interrruptionID` = ? AND `efectedcustomer`.`customerID` = ?"; //delete effected customers

			String query2 = "SELECT * FROM `efectedcustomer` WHERE `efectedcustomer`.`interrruptionID` = "+interruptionID; //get current effected customer

			String query3 = "INSERT INTO `efectedcustomer` (`interrruptionID`, `customerID`) VALUES (?, ?)"; //insert newer effected customer

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query2);

			String[] new_list = list;
			List<String>array = new ArrayList<String>();


			while(resultSet.next()) {
				array.add(resultSet.getString("customerID"));
			}

			preparedStatement = connection.prepareStatement(query);
			for (String string : array) {
				preparedStatement.setInt(1, interruptionID);
				preparedStatement.setString(2, string);
				preparedStatement.execute();
			}
			preparedStatement = connection.prepareStatement(query3);
			for (String custID : new_list) {
				preparedStatement.setInt(1, interruptionID);
				preparedStatement.setString(2, custID);
				preparedStatement.execute();
			}

			connection.close();

			output = "Updated Successfully";
			query = "";

		} catch (Exception e) {
			// TODO: handle exception
			output = "Error while inserting Updating";
			System.err.println(e.getMessage());
		}

		return output;
	}


	//	=====================================DELETE LOGIC=========================================
	@Override
	public String deleteInterruption(int id) {
		// TODO Auto-generated method stub
		

		
		String output = "";

		try {
			connection = connect();

			if (connection == null) {
				output = "Error while conecting to the database for Deleting interruption";
				return output;
			}

			query = "DELETE FROM interruption WHERE `interruption`.`id` = ?"; //delete interruptions
			
			//executing delete interruption query
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, id);

			preparedStatement.execute();

			connection.close();

			output = "Delete Successfully";
			query = "";

		} catch (Exception e) {
			// TODO: handle exception
			output = "Error while inserting Deleting";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
