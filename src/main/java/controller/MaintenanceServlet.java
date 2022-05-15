package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Interruption;
import service.MaintenanceService;
import service.MaintenencrServiceImpl;

/**
 * Servlet implementation class MaintenanceServlet
 */
@WebServlet("/MaintenanceServlet")
public class MaintenanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private MaintenanceService maintenanceService = new MaintenencrServiceImpl();
    private Interruption interruption = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MaintenanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String output = maintenanceService.allInterruptions(); // get all interruptions
		// TODO Auto-generated method stub

		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String intType = request.getParameter("intType");
		String intDesc = request.getParameter("desc");
		String intTitle = request.getParameter("title");
		String intSdate = Convert24(request.getParameter("sDate"));
		String intEdate = Convert24(request.getParameter("eDate"));
		intEdate = Convert24(intEdate);
		String intCustList = request.getParameter("custIDs");
		
		String[] list = intCustList.split(",");

		List<String> custList =new ArrayList<>();
		for (String string : list) {
			custList.add(string);
		}

		String intApproval = request.getParameter("approval");
		String intHandle = request.getParameter("handledby");
		interruption = new Interruption(intType, intTitle, intDesc, intSdate, intEdate, custList, intApproval, intHandle);
		
		String output = maintenanceService.insertInterruption(interruption);
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

				
				Map paras = getParasMap(request);
				int id = Integer.parseInt(paras.get("hiddenIDSAve").toString());
				String intType = paras.get("intType").toString();
				String intDesc = paras.get("desc").toString();
				String intTitle = paras.get("title").toString();
				String intSdate = Convert24(paras.get("sDate").toString());
				System.out.println(intSdate);
				String intEdate = Convert24(paras.get("eDate").toString());
				intEdate = Convert24(intEdate);
				System.out.println(intEdate);
				String intCustList = paras.get("custIDs").toString();
				
				String[] list = intCustList.split(",");

				List<String> custList =new ArrayList<>();
				for (String string : list) {
					custList.add(string);
				}

				String intApproval = paras.get("approval").toString();
				String intHandle = paras.get("handledby").toString();
				
				interruption = new Interruption(intType, intTitle, intDesc, intSdate, intEdate, custList, intApproval, intHandle);
				interruption.setId(id);

				String output = maintenanceService.updateInterruption(interruption);
				response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map paras = getParasMap(request);
		int id = Integer.parseInt(paras.get("intID").toString());
		String output = maintenanceService.deleteInterruption(id);
		response.getWriter().write(output);
	}

	private String Convert24(String date) {
		date = date.replace("T", " ");
		date = date.replace("%3A", ":");
		String dateTime = date;
		return dateTime; // return adjusted time or original string
	}

	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request) 
	{ 
		Map<String, String> map = new HashMap<String, String>(); 
		try
		{ 
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
			String queryString = scanner.hasNext() ? 
					scanner.useDelimiter("\\A").next() : ""; 

			scanner.close(); 
			String[] params = queryString.split("&"); 
			for (String param : params) 
			{ 
				String[] p = param.split("="); 
				map.put(p[0], p[1].replace("+", " ")); 
			} 
		} 
		catch (Exception e) 
		{ 
		} 
		return map; 
	}

}
