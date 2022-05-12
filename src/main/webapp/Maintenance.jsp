<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="controller.MaintenanceServlet,javax.servlet.jsp.PageContext" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Maintenance Page</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<h1>Interruption Manager</h1>
		<br>
		<div class="alert alert-success" id="alertSuccess">
		</div>
		<div class="alert alert-danger" id="alertError">
		</div>
		<form id="formData" name="formData">
			<div class="form-row">
				<div class="form-group col-md-8">
					<label for="title">Input Title</label>
					<input type="text" class="form-control" id="title" name="title">
				  </div>
				<div class="form-group col-md-4">
					<label for="intType">Select Interrupt Type</label>
					<select id="intType" class="form-control"  name="intType"> 
					  <option selected value="" id="selectedInt">Choose...</option>
					  <option value="Demand maintein">Demand maintain</option>
					  <option value="Special Request">Special Request</option>
					  <option value="Emergency">Emergency</option>
					  <option value="Power Failier">Power Failure</option>
					</select>
				  </div>
			</div>
			  <div class="form-group">
				<label for="desc">Input Description</label>
				<input type="text" class="form-control" id="desc" name="desc">
			  </div>
			<div class="form-group">
			  <label for="custIDs">Effected Customers</label>
			  <input type="text" class="form-control" id="custIDs" name="custIDs">
			</div>
			<div class="form-row">
			  <div class="form-group col-md-6">
				<label for="inputCity">Start Date</label>
				<input type="datetime-local" class="form-control" id="sDate" name="sDate">
			  </div>
			  
			  <div class="form-group col-md-6">
				<label for="inputState">End Date</label>
				<input type="datetime-local" class="form-control" id="eDate" name="eDate">
			  </div>
	
			  <div class="form-group col-md-12">
				<label for="approval">Approval</label>
				<select id="approval" class="form-control"  name="approval"> 
					<option selected value="Pending">Pending</option>
					<option value="Approved">Approved</option>
				  </select>
			  </div>
			</div>
			<input type="hidden" id="hiddenIDSAve" value="">
			<button type="button" class="btn btn-primary" id="btnSave">Save</button>
		  </form>

	</div>
	<br>
	<div class="container-fluid">
	<input type="hidden" id="contextPath" value=pageContext.request.contextPath>
		<div class="gridView">
		
		</div>
	</div>
</body>
<!-- <script type="text/javascript">document.getElementById("sDate").value = "2018-06-12T19:30"</script> -->
<script src="Components/jquery.min.js" type="text/javascript"></script>
<script src="Components/Maintenance.js" type="text/javascript"></script>
</html>