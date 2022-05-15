<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="controller.MaintenanceServlet,javax.servlet.jsp.PageContext"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Maintenance Page</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
    <a class="navbar-brand" href="#">E/G Power Grid</a>
    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
      <li class="nav-item ">
        <a class="nav-link" href="#">Home </a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="/Maintenance">Maintenance<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link disabled" href="#">Dashboard</a>
      </li>
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
  </div>
</nav>
	<div class="container">
		<h1>Interruption Manager</h1>
		<br>
		<div class="alert alert-success alert-dismissible fade show" role="alert"
			id="alertSuccess">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<div class="alert alert-danger alert-dismissible fade show" role="alert"
			id="alertError">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<form id="formData" name="formData">
			<div class="form-row">
				<div class="form-group col-md-8">
					<label for="title">Input Title</label> 
					<input type="text" class="form-control" id="title" name="title">
					<p id='validateTitle' style="color:red"> </p>
				</div>
				<div class="form-group col-md-4">
					<label for="intType">Select Interrupt Type</label> <select
						id="intType" class="form-control" name="intType">
						<option selected value="" id="selectedInt">Choose...</option>
						<option value="Demand maintain">Demand maintain</option>
						<option value="Special Request">Special Request</option>
						<option value="Emergency">Emergency</option>
						<option value="Power Failier">Power Failure</option>
					</select>
					<p id='validateIntType' style="color:red"> </p>
				</div>
			</div>
			<div class="form-group">
				<label for="desc">Input Description</label> <input type="text"
					class="form-control" id="desc" name="desc">
					<p id='validateDesc' style="color:red"> </p>
			</div>
			<div class="form-group">
				<label for="custIDs">Effected Customers</label> <input type="text"
					class="form-control" id="custIDs" name="custIDs">
					<p id='validateCustIDs' style="color:red"> </p>
			</div>
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="sDate">Start Date</label> <input type="datetime-local"
						class="form-control" id="sDate" name="sDate">
						<p id='validateSDate' style="color:red"> </p>
				</div>

				<div class="form-group col-md-6">
					<label for="eDate">End Date</label> <input type="datetime-local"
						class="form-control" id="eDate" name="eDate">
						<p id='validateEDate' style="color:red"> </p>
				</div>

				<div class="form-group col-md-6">
					<label for="approval">Approval</label> <select id="approval"
						class="form-control" name="approval">
						<option selected value="Pending">Pending</option>
						<option value="Approved">Approved</option>
					</select>
				</div>
				<div class="form-group col-md-6">
					<label for="approval">Handle By</label> <input type="text"
						class="form-control" id="handledby" name="handledby">
						<p id='validateHandledBy' style="color:red"> </p>
				</div>
			</div>

			<input type="hidden" name="hiddenIDSAve" id="hiddenIDSAve" value="">

			<button type="button" class="btn btn-primary" id="btnSave">Save</button>
		</form>

	</div>
	<br>
	<div class="container-fluid">
		<input type="hidden" id="contextPath"
			value=pageContext.request.contextPath>
		<div class="gridView"></div>
	</div>
</body>

<script src="Components/jquery.min.js" type="text/javascript"></script>
<script src="Components/Maintenance.js" type="text/javascript"></script>
</html>